package com.tqz.seata.product.service;

import com.tqz.seata.product.util.ResultData;

/**
 * <p>Product Service 接口层
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:47
 */
public interface ProductService {

    /**
     * 扣减库存
     */
    ResultData deduct(String productCode, Integer count);
}
