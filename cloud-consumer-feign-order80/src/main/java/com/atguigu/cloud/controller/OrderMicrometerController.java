package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author denghp
 * @create 2026-05-13 10:05
 */
@RestController
@Slf4j
public class OrderMicrometerController {

    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping(value = "/feign/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id){
        return payFeignApi.myMicrometer(id);
    }

}
