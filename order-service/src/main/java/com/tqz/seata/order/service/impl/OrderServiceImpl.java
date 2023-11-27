package com.tqz.seata.order.service.impl;

import com.tqz.seata.order.dto.OrderDTO;
import com.tqz.seata.order.mapper.OrderMapper;
import com.tqz.seata.order.po.Order;
import com.tqz.seata.order.service.OrderService;
import com.tqz.seata.order.util.ResultData;
import com.tqz.seata.order.util.ReturnCode;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * <p>订单的服务实现类
 *
 * @author tianqingzhao
 * @since 2021/7/12 17:01
 */
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    @Value("${service.name.account}")
    private String serviceNameAccount;

    @Value("${service.name.product}")
    private String serviceNameProduct;

    @GlobalTransactional(name = "TX_ORDER_CREATE")
    @Override
    public ResultData createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        orderMapper.insert(order);
        log.info("ORDER XID is: {}", RootContext.getXID());

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
                return ResultData.success("下单成功！");
            }
        }
        throw new RuntimeException("下单失败！");
    }
}
