package com.atguigu.springcloud;

import ch.qos.logback.core.db.DBHelper;
import com.atguigu.springcloud.config.MyConfig;
import com.atguigu.springcloud.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class MainApp8401 {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MainApp8401.class, args);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }

        System.out.println("------------------------");

        MyConfig myConfig = applicationContext.getBean(MyConfig.class);
        System.out.println(myConfig);

        MyConfig myConfig2 = applicationContext.getBean(MyConfig.class);
        System.out.println(myConfig2);

        User user = myConfig.user01();
        System.out.println(user);
        User user1 = myConfig.user01();
        System.out.println(user1);

        System.out.println(user1 == user);

        System.out.println("----------------------------");

        // 获取组件
        String[] beanNamesForType = applicationContext.getBeanNamesForType(User.class);
        for (String name : beanNamesForType) {
            System.out.println(name);
        }

        DBHelper dbHelper = applicationContext.getBean(DBHelper.class);
        System.out.println(dbHelper);

    }
}