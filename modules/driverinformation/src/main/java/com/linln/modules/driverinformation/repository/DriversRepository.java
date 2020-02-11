package com.linln.modules.driverinformation.repository;

import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author 程亮
 * @date 2020/01/16
 */
public interface DriversRepository extends JpaRepository<Drivers, Integer> {

    /**
     * 根据id删除
     * @param id
     */
    public void removeDriversById(Integer id);




}