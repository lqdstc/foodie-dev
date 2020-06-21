package com.imooc.pojo.contet.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@ApiModel(value = "用户信息BO", description = "从客户端，由用户传入的数据封装在此entity中")
public class CenterUsersBO {


    @ApiModelProperty(value = "用户名", name = "username", example = "imooc", required = true)
    private String username;

    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过12位")
    @ApiModelProperty(value = "昵称", name = "nickname", example = "imooc", required = false)
    private String nickname;

    @Length(max = 12, message = "真实姓名不能超过12位")
    @ApiModelProperty(value = "真实姓名", name = "realname", example = "imooc", required = false)
    private String realname;

    @ApiModelProperty(value = "头像", name = "face", example = "imooc", required = false)
    private String face;

    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", message = "手机号格式不正常")
    @ApiModelProperty(value = "手机号 手机号", name = "mobile", example = "imooc", required = false)
    private String mobile;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱地址 邮箱地址", name = "email", example = "imooc", required = false)
    private String email;

    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    @ApiModelProperty(value = "性别 性别 1:男  0:女  2:保密", name = "sex", example = "imooc", required = false)
    private Integer sex;

    @ApiModelProperty(value = "生日 生日", name = "birthday", example = "imooc", required = false)
    private Date birthday;
}
