package com.linln.modules.recruitdriver.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.recruitdriver.domain.RecruitDriver;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 程亮
 * @date 2020/02/07
 */
public interface RecruitDriverService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<RecruitDriver> getPageList(Example<RecruitDriver> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    RecruitDriver getById(Integer id);

    /**
     * 保存数据
     * @param recruitDriver 实体对象
     */
    RecruitDriver save(RecruitDriver recruitDriver);


}