package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBo;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    /**
     * 创建订单相关信息
     *
     * @param submitOrderBo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createOrder(SubmitOrderBo submitOrderBo) {

        //获取用户信息
        String userId = submitOrderBo.getUserId();
        String addressId = submitOrderBo.getAddressId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        Integer payMethod = submitOrderBo.getPayMethod();
        String leftMsg = submitOrderBo.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount = 0;


        //生成订单id
        String orderId = sid.nextShort();

        //通过用户id 和地址id 查询用户地址信息 调用 addressService接口
        UserAddress address = addressService.queryUserAddres(userId, addressId);

        //1.新订单数据保存
        Orders newOrder = new Orders();
        //订单id 保存
        newOrder.setId(orderId);
      newOrder.setUserId(userId);
        //1.2将用户地址信息存入订单中
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        //省市区
        newOrder.setReceiverAddress(address.getProvince() + "" + address.getCity() + "" + address.getDistrict() + "" + address.getDetail());
        //1.3将订单价格存入订单中
        //   newOrder.setTotalAmount();
        //    newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount); //邮费
        //支付方式
        newOrder.setPayMethod(payMethod);
        //用户备注
        newOrder.setLeftMsg(leftMsg);
        //是否评价
        newOrder.setIsComment(YesOrNo.NO.type);
        //是否删除
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());


        //2.循环根据itemSpecIds商品规格ids 商品规格为1,2,3,4 保存订单商品信息表
        //将字符串 变为数组
        String itemSpecIdArr[] = itemSpecIds.split(",");
        Integer totalAmount = 0; //商品原价累积
        Integer realPayAmount = 0; //真实付款价格
        for (String itemSpecId : itemSpecIdArr) {
            //todo 整合redis 后 商品购买的数量 重新从redis的购物车中获取
            int buyCounts = 1;
            //2.1根据规格id 查询规格对象
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            //2.2根据规格id 获得商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            //2.3循环保存订单到数据库
            String subOrderId = sid.nextShort(); //生成订单Id
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);//规格id
            subOrderItem.setItemSpecName(itemsSpec.getName()); //规格名称
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            //订单商品关联表 插入数据库
            orderItemsMapper.insert(subOrderItem);

            //2.4减库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }

        //订单表插入
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);


        //3.保存订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);

        return orderId;
    }
}
