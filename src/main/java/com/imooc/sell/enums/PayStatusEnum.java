package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 支付状态枚举
 *
 * @author lei
 */
@Getter
public enum PayStatusEnum {

    /**
     * 支付状态枚举
     */
    WAIT (0,"等待支付"),
    SUCCESS (1,"支付成功"),
    CANCEL (2, "取消支付")
    ;

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
