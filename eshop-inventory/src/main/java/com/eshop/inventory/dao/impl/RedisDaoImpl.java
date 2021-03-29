package com.eshop.inventory.dao.impl;

import com.eshop.inventory.dao.RedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:56
 */
@Repository
public class RedisDaoImpl implements RedisDao {

    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private JedisSentinelPool jedisSentinelPool;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
        // Jedis jedis = jedisSentinelPool.getResource();
        // jedis.set(key, value);
    }

    @Override
    public String get(String key) {
        String value = jedisCluster.get(key);

        // Jedis jedis = jedisSentinelPool.getResource();
        // String value = jedis.get(key);
        return value;
    }
}
