package com.tqz.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 订单服务启动类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:50
 */
@SpringBootApplication(scanBasePackages = "com.tqz")
@EnableFeignClients
public class TccOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccOrderServiceApplication.class, args);
    }

}
