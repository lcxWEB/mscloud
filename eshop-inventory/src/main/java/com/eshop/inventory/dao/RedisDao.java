package com.eshop.inventory.dao;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:56
 */
public interface RedisDao {

    void set(String key, String value);

    String get(String key);
}
