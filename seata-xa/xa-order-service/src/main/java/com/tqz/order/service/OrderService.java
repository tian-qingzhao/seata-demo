package com.tqz.order.service;

import com.tqz.datasource.entity.Order;
import com.tqz.order.vo.OrderVo;
import io.seata.core.exception.TransactionException;

public interface OrderService {

    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo) throws TransactionException;
}