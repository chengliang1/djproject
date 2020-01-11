package com.linln.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    /**
     * 跳转到注册页面
     */
    @GetMapping("/register")
    public String toLogin(Model model) {
//        model.addAttribute("isCaptcha", false);
        return "/register";
    }
}
