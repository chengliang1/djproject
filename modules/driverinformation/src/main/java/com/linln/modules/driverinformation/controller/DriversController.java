package com.linln.modules.driverinformation.controller;

import com.linln.modules.driverinformation.validator.DriversValid;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.driverinformation.service.DriversService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 程亮
 * @date 2020/01/16
 */
@Controller
@RequestMapping("/driverinformation/drivers")
public class DriversController {

    @Autowired
    private DriversService driversService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("driverinformation:drivers:index")
    public String index(Model model, Drivers drivers) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", match -> match.contains());

        // 获取数据列表
        Example<Drivers> example = Example.of(drivers, matcher);
        Page<Drivers> list = driversService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/driverinformation/drivers/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("driverinformation:drivers:add")
    public String toAdd() {
        return "/driverinformation/drivers/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("driverinformation:drivers:edit")
    public String toEdit(@PathVariable("id") Drivers drivers, Model model) {
        model.addAttribute("drivers", drivers);
        return "/driverinformation/drivers/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"driverinformation:drivers:add", "driverinformation:drivers:edit"})
    @ResponseBody
    public ResultVo save(@Validated DriversValid valid, Drivers drivers) {
        // 复制保留无需修改的数据
        if (drivers.getId() != null) {
            Drivers beDrivers = driversService.getById(drivers.getId());
            EntityBeanUtil.copyProperties(beDrivers, drivers);
        }

        // 保存数据
        driversService.save(drivers);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("driverinformation:drivers:detail")
    public String toDetail(@PathVariable("id") Drivers drivers, Model model) {
        model.addAttribute("drivers",drivers);
        return "/driverinformation/drivers/detail";
    }
}