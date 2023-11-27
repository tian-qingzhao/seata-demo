package com.tqz.seata.account.feign.fallback;

import com.tqz.seata.account.feign.AccountFeignClient;
import com.tqz.seata.account.util.ResultData;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * <p>账户降级工厂类
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:56
 */
@Slf4j
@Component
public class AccountFallbackFactory implements FallbackFactory<AccountFeignClient> {

    @Override
    public AccountFeignClient create(Throwable cause) {
        return new AccountFeignClient() {

            @Override
            public ResultData<String> reduce(String accountCode, BigDecimal amount) {
                return ResultData.fail(cause.getMessage());
            }
        };
    }
}
