package com.tqz.seata.account.controller;

import com.tqz.seata.account.service.AccountService;
import com.tqz.seata.account.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>account控制器
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:57
 */
@Api(tags = "account接口")
@Log4j2
@RestController
@RequestMapping("account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    public final AccountService accountService;

    @ApiOperation(value = "扣减余额", notes = "扣减余额")
    @PostMapping("/reduce")
    public ResultData<String> reduce(@RequestParam("accountCode") String accountCode,
                                     @RequestParam("amount") BigDecimal amount) {
        log.info("reduce account amount, accountCode is :{},amount is {} ", accountCode, amount);
        return accountService.reduceAccount(accountCode, amount);
    }
}
