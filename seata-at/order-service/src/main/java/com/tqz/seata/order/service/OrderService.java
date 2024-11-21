package com.tqz.seata.order.service;

import com.tqz.seata.order.dto.OrderDTO;
import com.tqz.seata.order.po.Order;
import com.tqz.seata.order.util.ResultData;

/**
 * <p>OrderService
 *
 * @author tianqingzhao
 * @since 2021/7/12 17:01
 */
public interface OrderService {

    /**
     * 下单接口
     *
     * @param orderDTO
     * @return
     */
    ResultData createOrder(OrderDTO orderDTO);

    void insert(Order order);

    void testCirculation();
}
