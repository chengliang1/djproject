package com.linln.modules.recruitdriver.controller;

import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.driverinformation.service.DriversService;
import com.linln.modules.recruitdriver.validator.RecruitDriverValid;
import com.linln.modules.recruitdriver.domain.RecruitDriver;
import com.linln.modules.recruitdriver.service.RecruitDriverService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author 程亮
 * @date 2020/02/07
 */
@Controller
@RequestMapping("/recruitdriver/recruitDriver")
public class RecruitDriverController {

    @Autowired
    private RecruitDriverService recruitDriverService;

    @Autowired
    private DriversService driversService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("recruitdriver:recruitDriver:index")
    public String index(Model model, RecruitDriver recruitDriver) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<RecruitDriver> example = Example.of(recruitDriver, matcher);
        Page<RecruitDriver> list = recruitDriverService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/recruitdriver/recruitDriver/index";
    }

    /**
     * 跳转到详细页面
     * @param recruitDriver
     * @param model
     * @return
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("recruitdriver:recruitDriver:detail")
    public String toDetail(@PathVariable("id") RecruitDriver recruitDriver,Model model){
        model.addAttribute("recruitDriver",recruitDriver);
        return "/recruitdriver/recruitDriver/detail";
    }



    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    //@RequiresPermissions({"recruitdriver:recruitDriver:add", "recruitdriver:recruitDriver:edit"})
    public String save(@Validated RecruitDriverValid valid, RecruitDriver recruitDriver) {
        recruitDriver.setCreateDate(new Date());
        recruitDriver.setRecruit_status(2);
        // 保存数据
        recruitDriverService.save(recruitDriver);
        return "/system/main/recruitment";
    }


    /**
     * 录用
     * @param drivers
     * @return
     */
    @GetMapping("/employ/{id}")
    @ResponseBody
    //@RequiresPermissions("recruitdriver:recruitDriver:employ")
    public ResultVo employ(@PathVariable("id") Integer id, Drivers drivers) {
        RecruitDriver recruitDriver = recruitDriverService.getById(id);
        if (recruitDriver.getRecruit_status() == 1){
            return ResultVoUtil.error("该申请已处理");
        }else {
        String drivername1 = recruitDriver.getDrivername();
        String xing = drivername1.substring(0, 1);
        String drivername = xing + "师傅";
        drivers.setDrivername(drivername);
        drivers.setName(drivername1);
        drivers.setIdCard(recruitDriver.getIdCard());
        drivers.setSex(recruitDriver.getSex());

        String brand[] = {"大众", "宝马", "奔驰", "奥迪A6", "迈巴赫", "别克", "奥迪A4", "雪佛兰", "兰博基尼", "法拉利", "野马", "捷豹", "保时捷", "长安", "中国红旗", "雷诺", "丰田", "北京现代"};
        int index = (int) (Math.random() * brand.length);
        String car_brand = brand[index];
        drivers.setCar_brand(car_brand);

        String licence[] = {"冀A·AS435", "皖H·WE555", "京Q·AQ333", "皖M·FG520", "皖A·DF568", "浙B·DR342", "苏A·34RED", "冀A·AA435", "皖M·FT764", "皖A·000000", "皖H·520CL", "皖A·1314J", "皖H·RF543", "皖A·6532D", "苏D·88888", "皖M·DF421", "皖M·FG444", "皖M·6T533", "皖A·FG453", "皖M·DR421", "皖M·555MG", "皖M·DF324", "皖M·DG213", "皖M·ER432", "皖M·AQ121", "皖M·CC343", "皖M·TY675", "皖M·DF421", "皖M·45D4S", "皖M·56TGD", "皖M·DERF3", "皖M·GT4F4", "皖M·GT543",};
        int index1 = (int) (Math.random() * licence.length);
        String licence1 = licence[index1];
        drivers.setLicence(licence1);
        drivers.setPhone(recruitDriver.getPhone());

        drivers.setLicence_num(recruitDriver.getLicence_num());

        String color[] = {"红色","蓝色","黑色","银白色","白色","灰色","褐色","黑色","蓝色","黑色","黑色","白色","白色","黑色","白色"};
        int index2 = (int) (Math.random() * color.length);
        String car_color=color[index2];
        drivers.setCar_color(car_color);

        int star[] ={1,2,3,4,5};
        int index3 = (int) (Math.random() * star.length);
        Integer driver_star = star[index3];
        drivers.setDriver_star(driver_star);

        drivers.setOrder_num(1);
        drivers.setMoney(1f);
        drivers.setWork(2);

        drivers.setDriver_time(recruitDriver.getDrive_time());

        drivers.setCreate_time(new Date());
        drivers.setCreate_time(new Date());

        driversService.save(drivers);

        recruitDriver.setRecruit_status(1);
        recruitDriverService.save(recruitDriver);

        return ResultVoUtil.success("录用成功");
        }
    }
}