package com.atguigu.cloud;

import java.time.ZonedDateTime;

/**
 * @author denghp
 * @create 2026-05-10 21:40
 */
public class Main {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);
    }
}