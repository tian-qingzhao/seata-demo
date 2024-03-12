package com.tqz.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 账户服务启动类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:50
 */
@SpringBootApplication(scanBasePackages = "com.tqz")
public class TccAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccAccountServiceApplication.class, args);
    }

}
