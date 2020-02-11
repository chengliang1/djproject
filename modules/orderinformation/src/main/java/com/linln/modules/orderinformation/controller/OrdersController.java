package com.linln.modules.orderinformation.controller;

import com.linln.modules.driverinformation.domain.Drivers;
import com.linln.modules.driverinformation.service.DriversService;
import com.linln.modules.orderinformation.validator.OrdersValid;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.orderinformation.domain.Orders;
import com.linln.modules.orderinformation.service.OrdersService;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 程亮
 * @date 2020/01/30
 */
@Controller
@RequestMapping("/orderinformation/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserService userService;

    @Autowired
    private DriversService driversService;

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

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    @ResponseBody
    @RequiresPermissions("orderinformation:orders:delete")
    public ResultVo deleteOrderById(@PathVariable("id") Integer id){
        ordersService.deleteById(id);
        return ResultVoUtil.success("删除成功");
    }


    /**
     * 小程序订单保存
     * @return
     */
    @GetMapping("/orderSave")
    public ResponseEntity<Boolean> programOrderSave(HttpServletRequest request, HttpServletResponse response){
        String orderid1 = request.getParameter("orderid");
        Long orderid = Long.parseLong(orderid1);
        String id1 = request.getParameter("id");
        Integer id = Integer.parseInt(id1);
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String order_date1 = request.getParameter("order_date");
        String year = "2020年";
        String order_date2 = year+order_date1;
        //日期转换
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");


        String order_type = request.getParameter("order_type");
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String distance1 = request.getParameter("distance");

        //距离类型转换
        float distance = Float.parseFloat(distance1);

        String duration1 = request.getParameter("duration");
        String unit = request.getParameter("unit");
        //时间拼接
        String duration = duration1+unit;

        String order_price1 = request.getParameter("order_price");
        float order_price = Float.parseFloat(order_price1);


        try {
            Date order_date = simpleDateFormat.parse(String.valueOf(order_date2));
            Orders orders = new Orders();
            orders.setOrderid(orderid);
            orders.setName(name);
            orders.setUsername(username);
            orders.setOrder_date(order_date);
            orders.setOrder_type(order_type);
            orders.setOrigin(origin);
            orders.setDestination(destination);
            orders.setDistance(distance);
            orders.setDuration(duration);
            orders.setOrder_price(order_price);
            orders.setCreateDate(new Date());
            ordersService.save(orders);

            //根据用户名找出该用户信息并更新balance
            User user = userService.getByName(username);
            //System.out.println(user.toString());
            Float balance1 = user.getBalance();
            Float balance = balance1 - order_price;
            user.setBalance(balance);
            userService.save(user);

            //根据司机名找出司机信息并更新money
            Drivers drivers = driversService.getById(id);
            Float money1 = drivers.getMoney();
            Float money = order_price + money1;
            drivers.setMoney(money);
            driversService.save(drivers);


        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期转换错误");
        }
        return ResponseEntity.ok(true);
    }

    /**
     * 小程序订单评价
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/evaluate")
    public ResponseEntity<Boolean> programEvaluate(HttpServletRequest request,HttpServletResponse response){
        String orderid1 = request.getParameter("orderid");
        String evaluateStar1 = request.getParameter("evaluatestar");
        Integer evaluateStar = Integer.parseInt(evaluateStar1);
        Long orderid = Long.parseLong(orderid1);
        Orders orders  = ordersService.getByOrderId(orderid);
        orders.setEvaluation_star(evaluateStar);
        ordersService.save(orders);
        return ResponseEntity.ok(true);
    }




}