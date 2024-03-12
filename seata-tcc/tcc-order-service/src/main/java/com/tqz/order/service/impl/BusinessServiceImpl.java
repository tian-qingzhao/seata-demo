package com.tqz.order.service.impl;

import com.tqz.datasource.entity.Order;
import com.tqz.order.feign.AccountFeignService;
import com.tqz.order.feign.StorageFeignService;
import com.tqz.order.service.BussinessService;
import com.tqz.order.service.OrderService;
import com.tqz.order.util.UUIDGenerator;
import com.tqz.order.vo.OrderVo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务实现类，用于操作订单并调用其他服务
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 18:05
 */
@Service
@Slf4j
public class BusinessServiceImpl implements BussinessService {

    @Autowired
    private AccountFeignService accountFeignService;

    @Autowired
    private StorageFeignService storageFeignService;

    @Autowired
    private OrderService orderService;


    @Override
    @GlobalTransactional(name = "createOrder", rollbackFor = Exception.class)
    public Order saveOrder(OrderVo orderVo) {
        log.info("=============用户下单=================");
        log.info("当前 XID: {}", RootContext.getXID());

        //获取全局唯一订单号  测试使用
        Long orderId = UUIDGenerator.generateUUID();

        //阶段一： 创建订单
        Order order = orderService.prepareSaveOrder(orderVo, orderId);

        //扣减库存
        storageFeignService.deduct(orderVo.getCommodityCode(), orderVo.getCount());
        //扣减余额
        accountFeignService.debit(orderVo.getUserId(), orderVo.getMoney());

        return order;
    }
}
