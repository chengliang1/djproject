package com.linln.modules.orderinformation.controller;

import com.linln.modules.orderinformation.validator.OrdersValid;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.orderinformation.domain.Orders;
import com.linln.modules.orderinformation.service.OrdersService;
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
 * @date 2020/01/30
 */
@Controller
@RequestMapping("/orderinformation/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("orderinformation:orders:index")
    public String index(Model model, Orders orders) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("order_date", match -> match.contains());

        // 获取数据列表
        Example<Orders> example = Example.of(orders, matcher);
        Page<Orders> list = ordersService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/orderinformation/orders/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("orderinformation:orders:add")
    public String toAdd() {
        return "/orderinformation/orders/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("orderinformation:orders:edit")
    public String toEdit(@PathVariable("id") Orders orders, Model model) {
        model.addAttribute("orders", orders);
        return "/orderinformation/orders/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"orderinformation:orders:add", "orderinformation:orders:edit"})
    @ResponseBody
    public ResultVo save(@Validated OrdersValid valid, Orders orders) {
        // 复制保留无需修改的数据
        if (orders.getId() != null) {
            Orders beOrders = ordersService.getById(orders.getId());
            EntityBeanUtil.copyProperties(beOrders, orders);
        }

        // 保存数据
        ordersService.save(orders);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("orderinformation:orders:detail")
    public String toDetail(@PathVariable("id") Orders orders, Model model) {
        model.addAttribute("orders",orders);
        return "/orderinformation/orders/detail";
    }


}