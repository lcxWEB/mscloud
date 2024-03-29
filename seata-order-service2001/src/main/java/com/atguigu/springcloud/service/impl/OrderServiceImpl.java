package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.OrderDao;
import com.atguigu.springcloud.domain.Order;
import com.atguigu.springcloud.service.AccountService;
import com.atguigu.springcloud.service.OrderService;
import com.atguigu.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 下订单->减库存->减余额->改状态
     *
     * @param order
     */
    @Override
    // @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("***** 开始新建订单");
        orderDao.create(order);

        log.info("***** 订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("***** 订单微服务开始调用库存，做扣减end");

        log.info("***** 订单微服务开始调用账户，做扣减money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("***** 订单微服务开始调用账户，做扣减end");

        // 1代表订单完成
        log.info("***** 修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("***** 修改订单状态结束");

        log.info("***** 下订单结束了，O(∩_∩)O哈哈~");
    }

}