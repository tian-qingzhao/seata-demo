package com.tqz.seata.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>订单DTO
 *
 * @author tianqingzhao
 * @since 2021/7/12 17:01
 */
@Data
public class OrderDTO implements Serializable {
    private Integer id;
    private String accountCode;
    private String productCode;
    private Integer count;
    private BigDecimal amount;
}
