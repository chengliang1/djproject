package com.linln.modules.driverinformation.repository;

import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程亮
 * @date 2020/01/16
 */
public interface DriversRepository extends JpaRepository<Drivers, Integer> {
}