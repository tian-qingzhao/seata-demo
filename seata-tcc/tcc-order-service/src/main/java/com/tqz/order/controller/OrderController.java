package com.tqz.order.controller;

import com.tqz.datasource.entity.Order;
import com.tqz.datasource.vo.ResultVo;
import com.tqz.order.service.BussinessService;
import com.tqz.order.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:49
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private BussinessService bussinessService;

    @PostMapping("/createOrder")
    public ResultVo createOrder(@RequestBody OrderVo orderVo) throws Exception {
        log.info("收到下单请求,用户:{}, 商品编号:{}", orderVo.getUserId(), orderVo.getCommodityCode());
        Order order = bussinessService.saveOrder(orderVo);
        return ResultVo.ok().put("order", order);
    }

}
