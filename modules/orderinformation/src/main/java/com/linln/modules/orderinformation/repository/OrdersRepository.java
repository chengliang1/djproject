package com.linln.modules.orderinformation.repository;

import com.linln.modules.orderinformation.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;


/**
 * @author 程亮
 * @date 2020/01/30
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {



}