package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: lichunxia
 * @create: 2020-11-24 23:13
 */
@SpringBootApplication
@EnableFeignClients
public class OrderFegin80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderFegin80.class, args);
    }
}