package com.tqz.datasource.entity;

import lombok.Data;

/**
 * 订单实体类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 18:04
 */
@Data
public class Order {
    private Long id;

    private String userId;
    /**
     * 商品编号
     */
    private String commodityCode;

    private Integer count;

    private Integer money;

    private Integer status;
}
