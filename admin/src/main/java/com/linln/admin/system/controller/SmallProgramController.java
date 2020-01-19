package com.linln.admin.system.controller;


import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.service.impl.SmallProgrammServeice;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class SmallProgramController {

    @Autowired
    private SmallProgrammServeice smallProgrammServeice;


    /**
     * 小程序登录
     *
     * @param smallUser
     */
    @PostMapping("/smallprogram")
    public ResponseEntity<Boolean> loginUser(@RequestBody User smallUser) {
        User u = smallProgrammServeice.smallLogin(smallUser);
        if (u==null){
            return ResponseEntity.ok(false);
        }
        String encrypt = ShiroUtil.encrypt(smallUser.getPassword(), u.getSalt());
        System.out.println(encrypt+"encrypt");
        if (StringUtils.equals(u.getPassword(),encrypt)){
            System.out.println(u+"----");
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.ok(false);
        }



    }


}
