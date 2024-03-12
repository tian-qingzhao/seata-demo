package com.tqz.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.tqz")
@EnableFeignClients
public class XAOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XAOrderServiceApplication.class, args);
    }

}
