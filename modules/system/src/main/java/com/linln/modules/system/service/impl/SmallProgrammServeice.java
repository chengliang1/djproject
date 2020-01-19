package com.linln.modules.system.service.impl;


import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.SmallRespontory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmallProgrammServeice {

    @Autowired
    private SmallRespontory smallRespontory;

    /**
     * 小程序登录
     * @param
     * @return
     */
    public User smallLogin(User user1) {

        User user = smallRespontory.findByUsername(user1.getUsername());
        if (user==null){
            return null;
        }
        return user;
    }
}
