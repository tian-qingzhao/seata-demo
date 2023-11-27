package com.tqz.seata.account.service.impl;

import com.tqz.seata.account.mapper.AccountMapper;
import com.tqz.seata.account.po.Account;
import com.tqz.seata.account.service.AccountService;
import com.tqz.seata.account.util.ResultData;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>Account Service 接口层
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:57
 */
@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ResultData reduceAccount(String accountCode, BigDecimal amount) {
        log.info("Account XID is: {}", RootContext.getXID());
        Account account = accountMapper.selectByCode(accountCode);
        if(null == account){
            throw new RuntimeException("can't reduce amount,account is null");
        }
        BigDecimal subAmount = account.getAmount().subtract(amount);
        if(subAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("can't reduce amount,account'amount is less than reduce amount");
        }
        account.setAmount(subAmount);
        int result = accountMapper.updateById(account);
//        int i = 1 / 0;
        if (result > 0) {
            return ResultData.success("扣减账户余额成功！");
        } else {
            return ResultData.fail("扣减账户余额失败！");
        }
    }
}
