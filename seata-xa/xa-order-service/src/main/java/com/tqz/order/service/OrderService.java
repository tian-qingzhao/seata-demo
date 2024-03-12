package com.tqz.order.service;

import com.tqz.datasource.entity.Order;
import com.tqz.order.vo.OrderVo;
import io.seata.core.exception.TransactionException;

/**
 * 订单接口
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:50
 */
public interface OrderService {

    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo) throws TransactionException;
}