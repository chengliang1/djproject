package com.linln.modules.recruitdriver.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.recruitdriver.domain.RecruitDriver;
import com.linln.modules.recruitdriver.repository.RecruitDriverRepository;
import com.linln.modules.recruitdriver.service.RecruitDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 程亮
 * @date 2020/02/07
 */
@Service
public class RecruitDriverServiceImpl implements RecruitDriverService {

    @Autowired
    private RecruitDriverRepository recruitDriverRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public RecruitDriver getById(Integer id) {
        return recruitDriverRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<RecruitDriver> getPageList(Example<RecruitDriver> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return recruitDriverRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param recruitDriver 实体对象
     */
    @Override
    public RecruitDriver save(RecruitDriver recruitDriver) {
        return recruitDriverRepository.save(recruitDriver);
    }


}