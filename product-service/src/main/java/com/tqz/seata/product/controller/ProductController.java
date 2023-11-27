package com.tqz.seata.product.controller;

import com.tqz.seata.product.service.ProductService;
import com.tqz.seata.product.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>商品控制器
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:42
 */
@RestController
@Log4j2
@Api(tags = "product模块")
@RequestMapping("product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping("/deduct")
    @ApiOperation(value = "扣减库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCode", value = "产品编码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "count", value = "产品数量", required = true, paramType = "query")
    })
    public ResultData<String> deduct(@RequestParam("productCode") String productCode, @RequestParam("count") Integer count) {
        log.info("deduct product, productCode is :{},count is {} ", productCode, count);
        return productService.deduct(productCode, count);
    }
}
