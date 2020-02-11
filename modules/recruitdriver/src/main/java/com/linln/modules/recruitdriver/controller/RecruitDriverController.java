package com.linln.modules.recruitdriver.controller;

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

import java.util.Date;

/**
 * @author 程亮
 * @date 2020/02/07
 */
@Controller
@RequestMapping("/recruitdriver/recruitDriver")
public class RecruitDriverController {

    @Autowired
    private RecruitDriverService recruitDriverService;

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
    @RequiresPermissions({"recruitdriver:recruitDriver:add", "recruitdriver:recruitDriver:edit"})
    public String save(@Validated RecruitDriverValid valid, RecruitDriver recruitDriver) {
        System.out.println(recruitDriver.toString());
        recruitDriver.setCreateDate(new Date());
        recruitDriver.setRecruit_status(2);
        // 保存数据
        recruitDriverService.save(recruitDriver);
        return "/system/main/recruitment";
    }


}