package com.eshop.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: lichunxia
 * @create: 2021-02-25 13:52
 */
@Configuration
public class JedisConfig {

    @Bean
    public JedisCluster jedisCluster() {
        //  JedisCluster(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout,
        //                       int maxAttempts, String password, final GenericObjectPoolConfig poolConfig)
        Set<HostAndPort> redisNodes = new HashSet<>();
        redisNodes.add(new HostAndPort("localhost", 7001));
        redisNodes.add(new HostAndPort("localhost", 7002));
        redisNodes.add(new HostAndPort("localhost", 7003));
        JedisCluster jedisCluster = new JedisCluster(redisNodes, 1000, 1000,
                3, new JedisPoolConfig());
        return jedisCluster;
    }


    @Bean
    public JedisSentinelPool jedisSentinelPool() {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("localhost:26379");
        sentinels.add("localhost:26380");
        sentinels.add("localhost:26381");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels, new JedisPoolConfig(),
                3000);
        return jedisSentinelPool;
    }


}
