package com.imooc.sell.repository;

import com.imooc.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lei
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     *  通过openid查找订单
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid,Pageable pageable);
}
