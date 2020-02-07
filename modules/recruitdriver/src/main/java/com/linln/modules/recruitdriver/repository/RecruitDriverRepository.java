package com.linln.modules.recruitdriver.repository;

import com.linln.modules.recruitdriver.domain.RecruitDriver;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程亮
 * @date 2020/02/07
 */
public interface RecruitDriverRepository extends JpaRepository<RecruitDriver, Integer> {
}