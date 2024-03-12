package com.tqz.order.service;

import com.tqz.datasource.entity.Order;
import com.tqz.order.vo.OrderVo;

/**
 * 业务接口
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 17:39
 */
public interface BussinessService {

    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo);
}
