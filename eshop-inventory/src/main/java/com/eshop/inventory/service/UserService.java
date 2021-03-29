package com.eshop.inventory.service;

import com.eshop.inventory.model.User;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:19
 */
public interface UserService {

    User findUserInfo();

    String getCachedUserInfo();

}
