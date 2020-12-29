package com.atguigu.springcloud.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: lichunxia
 * @create: 2020-12-29 08:23
 */
@Data
@Accessors(chain = true)
public class User {


    private String username;

}
