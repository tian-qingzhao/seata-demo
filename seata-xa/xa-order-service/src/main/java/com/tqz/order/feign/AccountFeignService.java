package com.tqz.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户服务feign接口
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:49
 */
@FeignClient(name = "xa-account-service", path = "/account")
@Repository
public interface AccountFeignService {

    @RequestMapping("/debit")
    Boolean debit(@RequestParam("userId") String userId, @RequestParam("money") int money);
}
