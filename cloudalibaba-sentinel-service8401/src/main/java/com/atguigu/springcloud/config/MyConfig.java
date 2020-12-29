package com.atguigu.springcloud.config;

import com.atguigu.springcloud.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lichunxia
 * @create: 2020-12-29 08:22
 */
@Configuration(proxyBeanMethods = false)
public class MyConfig {

    @Bean
    public User user01() {
        return new User().setUsername("222");
    }

}
