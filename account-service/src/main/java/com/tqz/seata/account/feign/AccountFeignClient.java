package com.tqz.seata.account.feign;

import com.tqz.seata.account.feign.fallback.AccountFallbackFactory;
import com.tqz.seata.account.util.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * <p>账户对外暴露接口
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:56
 */
@FeignClient(path = "account", name = "account-service", fallback = AccountFallbackFactory.class)
public interface AccountFeignClient {

    @PostMapping("reduce")
    ResultData<String> reduce(@RequestParam("accountCode") String accountCode, @RequestParam("amount") BigDecimal amount);

}
