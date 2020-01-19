package com.linln.modules.system.repository;

import com.linln.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SmallRespontory  extends JpaRepository<User,Long>{

    /**
     * 小程序登录
     * @param
     * @return
     */
    public User findByUsername(String username);
}
