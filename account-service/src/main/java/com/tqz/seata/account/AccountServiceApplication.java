package com.tqz.seata.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>account-service服务启动类
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:30
 */
@SpringBootApplication
@EnableFeignClients("com.tqz.seata.account.feign")
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class);
    }
}
