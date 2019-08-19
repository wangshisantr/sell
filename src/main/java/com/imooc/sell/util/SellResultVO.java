package com.imooc.sell.util;

import lombok.Data;

/**
 * 通用返回对象
 *
 * @author lei
 */
@Data
public class SellResultVO {
    /**
     * 编码 0成功
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体对象
     */
    private Object data;

    public SellResultVO(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public SellResultVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SellResultVO(Object data) {
        this.code = "0";
        this.msg = "成功";
        this.data = data;
    }

    public static SellResultVO success() {
        return new SellResultVO("0", "操作成功");
    }

    public static SellResultVO fail() {
        return new SellResultVO("1", "操作失败");
    }
    public static SellResultVO success(Object data) {
        return new SellResultVO("0", "操作成功", data);
    }

    public static SellResultVO fail(Object data) {
        return new SellResultVO("1", "操作失败", data);
    }
}
