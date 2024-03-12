package com.tqz.account.service;

/**
 * 账户接口
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:51
 */
public interface AccountService {

    /**
     * 用户账户扣款
     *
     * @param userId
     * @param money  从用户账户中扣除的金额
     */
    void debit(String userId, int money);
}