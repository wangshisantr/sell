package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "买家手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    @Size(max = 120)
    private String address;

    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}