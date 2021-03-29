package com.eshop.inventory.mapper;

import com.eshop.inventory.model.User;

import java.util.List;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:18
 */
public interface UserMapper {

    User findUserInfo();

    List<User> findAllUser();

}
