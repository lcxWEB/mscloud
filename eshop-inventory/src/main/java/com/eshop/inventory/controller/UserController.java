package com.eshop.inventory.controller;

import com.eshop.inventory.model.User;
import com.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:24
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUserInfo")
    public User getUserInfo() {
        User userInfo = userService.findUserInfo();
        if (userInfo != null) {
            System.out.println(userInfo.getUsername() + " : " + userInfo.getAge());
        }
        return userInfo;
    }

    @GetMapping("getCachedUserInfo")
    public String getCachedUserInfo() {
        // JedisClusterException: CLUSTERDOWN Hash slot not served
        String userInfo = userService.getCachedUserInfo();
        return userInfo;
    }

}
