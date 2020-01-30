package com.linln.modules.driverinformation.service.impl;

import com.linln.BootApplication;
import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.driverinformation.repository.DriversRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

public class DriversServiceImplTest {

    @Autowired
    private DriversRepository driversRepository;

    @Test
    public List<Drivers> getAllInfo() {
        Drivers drivers = new Drivers();
        drivers.setWork(2);
        Example<Drivers> driversExample = Example.of(drivers);
        List<Drivers> driverinfo = driversRepository.findAll(driversExample);
        System.out.println(driverinfo.toString());
        return driverinfo;
    }
}