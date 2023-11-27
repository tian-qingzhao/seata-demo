package com.tqz.seata.order.controller;

import com.tqz.seata.order.dto.OrderDTO;
import com.tqz.seata.order.service.OrderService;
import com.tqz.seata.order.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>order控制器
 *
 * @author tianqingzhao
 * @since 2021/7/12 17:01
 */
@Api(value = "订单接口", tags = "订单接口")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "下单", notes = "下单接口")
    @PostMapping("/create")
    public ResultData create(OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }
}
