package com.imooc.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * @author wanglei
 */
@Entity
@Data
public class OrderMaster {

    /**主键*/
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /**订单总金额*/
    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;
}
