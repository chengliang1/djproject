package com.linln.modules.orderinformation.repository;

import com.linln.modules.orderinformation.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author 程亮
 * @date 2020/01/30
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    /**
     * 根据订单编号查询订单
     * @param orderid
     * @return
     */
    Orders findByOrderid(Long orderid);

    /**
     * 根据id删除
     * @param id
     */
    void removeOrdersById(Integer id);


}