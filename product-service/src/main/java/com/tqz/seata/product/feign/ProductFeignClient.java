package com.tqz.seata.product.feign;

import com.tqz.seata.product.feign.fallback.ProductFallbackFactory;
import com.tqz.seata.product.util.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>ProductFeign 对外暴露接口
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:47
 */
@FeignClient(path = "/product", value = "product-service", fallback = ProductFallbackFactory.class)
public interface ProductFeignClient {

    /**
     * 扣减库存
     *
     * @param productCode
     * @param count
     * @return
     */
    @PostMapping("/deduct")
    ResultData<String> deduct(@RequestParam("productCode") String productCode, @RequestParam("count") Integer count);
}
