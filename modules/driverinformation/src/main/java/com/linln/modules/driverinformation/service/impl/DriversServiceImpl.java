package com.linln.modules.driverinformation.service.impl;

import com.linln.common.data.PageSort;
import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.driverinformation.repository.DriversRepository;
import com.linln.modules.driverinformation.service.DriversService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author 程亮
 * @date 2020/01/16
 */
@Service
public class DriversServiceImpl implements DriversService {

    @Autowired
    private DriversRepository driversRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Drivers getById(Integer id) {
        return driversRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Drivers> getPageList(Example<Drivers> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return driversRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param drivers 实体对象
     */
    @Override
    public Drivers save(Drivers drivers) {
        return driversRepository.save(drivers);
    }


    /**
     * 小程序请求司机信息
     * @return
     */
    @Override
    public List<Drivers> getAllInfo(Drivers drivers) {
        Example<Drivers> driversExample = Example.of(drivers);
        List<Drivers> driverinfo = driversRepository.findAll(driversExample);
        return driverinfo;
    }

}