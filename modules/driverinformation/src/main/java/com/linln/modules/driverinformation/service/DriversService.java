package com.linln.modules.driverinformation.service;

import com.linln.modules.driverinformation.domain.Drivers;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 程亮
 * @date 2020/01/16
 */
public interface DriversService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Drivers> getPageList(Example<Drivers> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Drivers getById(Integer id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     */
    public void deleteBatch(List<Drivers> drivers);

    /**
     * 保存数据
     * @param drivers 实体对象
     */
    Drivers save(Drivers drivers);

    /**
     * 小程序请求司机信息
     * @return
     */
    List<Drivers> getAllInfo(Drivers drivers);
}