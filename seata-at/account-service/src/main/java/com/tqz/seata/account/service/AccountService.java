package com.tqz.seata.account.service;

import com.tqz.seata.account.util.ResultData;

import java.math.BigDecimal;

/**
 * <p>Account Service 接口层
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:57
 */
public interface AccountService {

    /**
     * 扣减账户余额
     *
     * @param accountCode 账号编码
     * @param amount      扣减的金额
     * @return
     */
    ResultData reduceAccount(String accountCode, BigDecimal amount);
}
