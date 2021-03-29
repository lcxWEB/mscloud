package com.eshop.inventory.service.impl;

import com.eshop.inventory.dao.RedisDao;
import com.eshop.inventory.mapper.UserMapper;
import com.eshop.inventory.model.User;
import com.eshop.inventory.service.UserService;
import org.springframework.stereotype.Service;

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

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public String getCachedUserInfo() {
        redisDao.set("cached_user", "\"name\" : \"lisi\", \"age\" : 28");
        String user = redisDao.get("cached_user");

        return user;
    }
}
