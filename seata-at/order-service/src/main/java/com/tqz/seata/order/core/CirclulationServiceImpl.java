package com.tqz.seata.order.core;

import cn.hutool.extra.spring.SpringUtil;
import com.tqz.seata.order.dto.OrderDTO;
import com.tqz.seata.order.mapper.OrderMapper;
import com.tqz.seata.order.po.Order;
import com.tqz.seata.order.service.OrderService;
import com.tqz.seata.order.util.ResultData;
import com.tqz.seata.order.util.ReturnCode;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 循环创建订单实现类.
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/11/21 10:35
 */
@Slf4j
@Service
public class CirclulationServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Value("${service.name.account}")
    private String serviceNameAccount;

    @Value("${service.name.product}")
    private String serviceNameProduct;

    @GlobalTransactional(rollbackFor = Exception.class, name = "CirclulationCreateOrder")
    public void createOrder(String accountCode) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCount(2);
        orderDTO.setAccountCode(accountCode);
        orderDTO.setProductCode("001");

        log.info("ORDER XID is: {}", RootContext.getXID());

        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        SpringUtil.getBean(OrderService.class).insert(order);

        MultiValueMap productParamsMap = new LinkedMultiValueMap();
        productParamsMap.add("productCode", orderDTO.getProductCode());
        productParamsMap.add("count", orderDTO.getCount());

        ResultData rs = restTemplate.postForObject(serviceNameProduct + "product/deduct", productParamsMap, ResultData.class);
        if (rs.getCode() == ReturnCode.RC100.getCode()) {

            MultiValueMap accountParamsMap = new LinkedMultiValueMap();
            accountParamsMap.add("accountCode", orderDTO.getAccountCode());
            accountParamsMap.add("amount", rs.getData());

            ResultData resultData = restTemplate.postForObject(serviceNameAccount + "account/reduce", accountParamsMap, ResultData.class);

            if (resultData.getCode() == ReturnCode.RC100.getCode()) {
                log.info("下单成功");
                return;
            }
        }
        throw new RuntimeException("下单失败！");
    }
}
