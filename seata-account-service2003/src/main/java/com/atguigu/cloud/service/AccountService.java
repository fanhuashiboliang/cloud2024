package com.atguigu.cloud.service;


/**
 * @author denghp
 * @create 2026-05-15 16:21
 */
public interface AccountService {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 本次消费金额
     */
    void decrease(Long userId, Long money);

}
