package com.atguigu.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author denghp
 * @create 2026-05-14 16:13
 */
@Service
public class FlowLimitService {

    @SentinelResource(value = "common")
    public void common()
    {
        System.out.println("------FlowLimitService come in");
    }
}
