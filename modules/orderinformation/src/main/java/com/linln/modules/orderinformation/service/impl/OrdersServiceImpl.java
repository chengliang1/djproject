package com.linln.modules.orderinformation.service.impl;

import com.linln.common.data.PageSort;
import com.linln.modules.orderinformation.domain.Orders;
import com.linln.modules.orderinformation.repository.OrdersRepository;
import com.linln.modules.orderinformation.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 程亮
 * @date 2020/01/30
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Orders getById(Integer id) {
        return ordersRepository.findById(id).orElse(null);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        ordersRepository.removeOrdersById(id);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Orders> getPageList(Example<Orders> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return ordersRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param orders 实体对象
     */
    @Override
    public Orders save(Orders orders) {
        return ordersRepository.save(orders);
    }

    /**
     * 根据订单编号查询订单
     * @param id
     * @return
     */
    @Override
    public Orders getByOrderId(Long id) {
        return ordersRepository.findByOrderid(id);
    }


}