package com.atguigu.springcloud.config;

import ch.qos.logback.core.db.DBHelper;
import com.atguigu.springcloud.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: lichunxia
 * @create: 2020-12-29 08:22
 * proxyBeanMethods：代理bean的方法 com.atguigu.springcloud.config.MyConfig$$EnhancerBySpringCGLIB$$aeca9d6a@79f90a3a
 *  Full：单例的
 *  Lite：新创建的
 *  组件依赖必须Full模式
 *
 * @Import({User.class, DBHelper.class})
 *  给容器中自动创建出这两个类型的组件
 *
 */
@Import({User.class, DBHelper.class})
@Configuration(proxyBeanMethods = false)
public class MyConfig {

    @Bean
    public User user01() {
        return new User().setUsername("222");
    }

}
