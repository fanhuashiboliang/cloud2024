package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Order;

/**
 * @author denghp
 * @create 2026-05-15 15:36
 */
public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);

}
