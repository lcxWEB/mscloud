package com.eshop.inventory.service.impl;

import com.eshop.inventory.dao.RedisDao;
import com.eshop.inventory.mapper.UserMapper;
import com.eshop.inventory.model.User;
import com.eshop.inventory.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:22
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisDao redisDao;

    @Transactional
    @Override
    public User findUserInfo() {
        UserServiceImpl userService = (UserServiceImpl) AopContext.currentProxy();
        userService.getCachedUserInfo();
        return userMapper.findUserInfo();
    }

    @Override
    public String getCachedUserInfo() {
        redisDao.set("cached_user", "\"name\" : \"lisi\", \"age\" : 28");
        String user = redisDao.get("cached_user");

        return user;
    }
}
