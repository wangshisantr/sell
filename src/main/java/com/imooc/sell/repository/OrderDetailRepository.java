package com.imooc.sell.repository;

import com.imooc.sell.domain.OrderDetail;
import com.imooc.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lei
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /**
     *  通过orderId查找订单详情
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
