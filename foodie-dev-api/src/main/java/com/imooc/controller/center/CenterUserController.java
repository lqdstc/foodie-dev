package com.imooc.controller.center;


import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.contet.bo.CenterUsersBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "用户信息", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUsersBO centerUsersBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {

        //validate 验证信息
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return JSONResult.errorMap(errorMap);
        }


        Users res = centerUserService.upDateUserInfo(userId, centerUsersBO);

        //更新cookie
        res = setNullProperty(res);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(res), true);

        //TODO 后续要改 ，增加令牌TOken  加入redis
        return JSONResult.ok();
    }


    //接收错误的属性跟信息
    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            //发生验证错误所对应的某个属性
            String errorField = error.getField();
            //验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }


    /**
     * 返回的cookie 设置为空
     *
     * @param userResult 用户信息状态
     * @return
     */
    private Users setNullProperty(Users userResult) {
        //返回的cookie 设置为空
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);

        return userResult;
    }


    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file, //文件
            HttpServletRequest request,
            HttpServletResponse response) {

        //定义头像保存的地址
        String fileSpace = IMAGE_USER_FACE_LOCATION;
        //在路径上位每一个用户增加userid,用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        //开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            try {
                //获得文件上传的文件名称
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    //文件重命名 imocc-face.png-> ["imooc.face","png]
                    String fileNameArr[] = fileName.split("\\.");

                    //获取文件后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    //文件名称重组  覆盖式上传    增量式：额外拼接当前时间
                    String newFileName = "face-" + userId + "." + suffix;
                    log.info("文件上传成功文件名为：" + newFileName);
                    //上传头像最终上传的地址
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                    File outFile = new File(finalFacePath);
                    //如果文件夹不存在
                    if (outFile.getParentFile() != null) {
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    //文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    // 文件输入流
                    InputStream inputStream = file.getInputStream();
                    //apache io流下的包
                    IOUtils.copy(inputStream, fileOutputStream);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else {

            return JSONResult.errorMsg("文件不能为空");
        }


        return JSONResult.ok();
    }
}
