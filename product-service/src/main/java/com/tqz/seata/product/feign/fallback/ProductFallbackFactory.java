package com.tqz.seata.product.feign.fallback;

import com.tqz.seata.product.feign.ProductFeignClient;
import com.tqz.seata.product.util.ResultData;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>商品降级工厂
 *
 * @author tianqingzhao
 * @since 2021/2/26 11:58
 */
@Slf4j
@Component
public class ProductFallbackFactory implements FallbackFactory<ProductFeignClient> {

    @Override
    public ProductFeignClient create(Throwable cause) {
        return new ProductFeignClient() {

            @Override
            public ResultData<String> deduct(String productCode, Integer count) {
                return ResultData.fail(cause.getMessage());
            }
        };
    }
}
