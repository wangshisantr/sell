package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imooc.sell.domain.OrderDetail;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单dto
 * @author lei
 */
@Data
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /**订单总金额*/
    private Integer orderStatus;

    private Integer payStatus;

    // DateTimeFormat：入参格式化，JsonFormat：出参格式化， GMT+8：东八区，避免时间在转换中有误差
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Date updateTime;

    /**订单详情列表*/
    List<OrderDetail> orderDetails;
}
