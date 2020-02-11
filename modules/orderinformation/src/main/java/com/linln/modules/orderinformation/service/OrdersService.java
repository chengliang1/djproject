package com.linln.modules.orderinformation.service;

import com.linln.modules.orderinformation.domain.Orders;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;



/**
 * @author 程亮
 * @date 2020/01/30
 */
public interface OrdersService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Orders> getPageList(Example<Orders> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Orders getById(Integer id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 保存数据
     * @param orders 实体对象
     */
    Orders save(Orders orders);

    /**
     * 根据订单编号查询订单
     * @param id
     * @return
     */
    Orders getByOrderId(Long id);

}