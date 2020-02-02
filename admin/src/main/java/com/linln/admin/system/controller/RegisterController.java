package com.linln.admin.system.controller;

import com.linln.common.enums.ResultEnum;
import com.linln.common.exception.ResultException;
import com.linln.component.actionLog.annotation.EntityParam;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.Role;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;


@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到注册页面
     */
    @GetMapping("/register")
    public String toLogin(Model model) {
        model.addAttribute("isCaptcha", false);
        return "/register";
    }

    /**
     * 实现注册
     * @return
     */
    @RequestMapping("/registers")
    public String register(@EntityParam User user, Model model){
        // 对密码进行加密
        String salt = ShiroUtil.getRandomSalt();
        String encrypt = ShiroUtil.encrypt(user.getPassword(), salt);
        user.setPassword(encrypt);
        user.setSalt(salt);
        // 判断用户名是否重复
        if (userService.repeatByUsername(user)) {
            throw new ResultException(ResultEnum.USER_EXIST);
        }
        //设置昵称
        user.setNickname("昵称");
        //设置初始金额
        user.setBalance(10000f);

        //设置权限为用户组
        Set<Role> roles = new HashSet<> ();
        Role role = new Role();
        role.setId(3l);
        roles.add(role);
        user.setRoles(roles);

        // 保存数据
        userService.save(user);
        return "/login";

    }
}
