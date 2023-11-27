package com.tqz.seata.product.service.impl;

import com.tqz.seata.product.mapper.ProductMapper;
import com.tqz.seata.product.po.Product;
import com.tqz.seata.product.service.ProductService;
import com.tqz.seata.product.util.ResultData;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>ProductService实现类
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:47
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ResultData deduct(String productCode, Integer deductCount) {
        log.info("PRODUCT XID is: {}", RootContext.getXID());
        Product product = productMapper.selectByCode(productCode);
        if (null == product) {
            throw new RuntimeException("can't deduct product,product is null");
        }
        int surplus = product.getCount() - deductCount;
        if (surplus < 0) {
            throw new RuntimeException("can't deduct product,product's count is less than deduct count");
        }
        product.setCount(surplus);
        int result = productMapper.updateById(product);

        if (result > 0) {
            BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(deductCount));
            return ResultData.success("下单成功！", totalPrice);
        } else {
            return ResultData.fail("下单失败！");
        }
    }
}
