package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author denghp
 * @create 2026-05-15 15:36
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private StorageFeignApi storageFeignApi;

    @Resource
    private AccountFeignApi accountFeignApi;

    @Override
    @GlobalTransactional(name="zzyy-create-order",rollbackFor = Exception.class)//AT
    public void create(Order order) {

        //xid全局事务id的检查，重要
        String xid = RootContext.getXID();

        //1.新建订单
        log.info("-----------------开始新建订单：" + "\t" + "xid: " + xid);

        //订单新建时默认初始订单状态为0
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);

        //插入订单成功后获得插入mysql的实体对象
        Order orderFromDB = null;

        if(result > 0){
            //从mysql里面查出刚插入的记录
            orderFromDB = orderMapper.selectOne(order);
            log.info("----------->新建订单成功，orderFromDB info: " + orderFromDB);
            System.out.println();
            //2.扣减库存
            log.info("----------->订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("----------->订单微服务开始调用Storage库存，做扣减完成");
            System.out.println();
            //3.扣减账户余额
            log.info("----------->订单微服务开始调用Account账号，做扣减money");
            accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            log.info("----------->订单微服务开始调用Account账号，做扣减完成");

            //4.修改订单状态
            //将订单状态从零修改为1，表示已经完成
            log.info("----------->修改订单状态开始");
            orderFromDB.setStatus(1);

            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId", orderFromDB.getUserId());
            criteria.andEqualTo("status", 0);

            int updateResult = orderMapper.updateByExampleSelective(orderFromDB, whereCondition);
            if(updateResult > 0){
                log.info("----------->修改订单状态成功");
                log.info("----------->orderFromDB info: " + orderFromDB);
            }else{
                log.info("----------->修改订单状态失败");
            }
        }
        System.out.println();

        log.info("-----------------结束新建订单：" + "\t" + "xid: " + xid);

    }
}
