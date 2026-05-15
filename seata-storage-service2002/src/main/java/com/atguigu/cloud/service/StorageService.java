package com.atguigu.cloud.service;

/**
 * @author denghp
 * @create 2026-05-15 16:04
 */
public interface StorageService {

    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);

}
