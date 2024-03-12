package com.tqz.order.vo;

import lombok.Data;

/**
 * 下单vo类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:50
 */
@Data
public class OrderVo {
    private String userId;
    /**
     * 商品编号
     **/
    private String commodityCode;

    private Integer count;

    private Integer money;
}
