package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;

/**
 * 自定义异常类
 * @author lei
 */
public class SellException extends RuntimeException {

    private static final long serialVersionUID = 2410978866525677530L;
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public SellException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }
}
