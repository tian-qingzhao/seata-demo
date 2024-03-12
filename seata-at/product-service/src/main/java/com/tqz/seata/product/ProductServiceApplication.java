package com.tqz.seata.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>account-service服务启动类
 *
 * @author tianqingzhao
 * @since 2021/7/12 17:02
 */
@SpringBootApplication
@EnableFeignClients("com.tqz.seata.product.feign")
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class);
    }
}