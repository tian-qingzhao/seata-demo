package com.tqz.account.service.impl;

import com.tqz.account.service.AccountService;
import com.tqz.datasource.entity.Account;
import com.tqz.datasource.mapper.AccountMapper;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账户实现类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 17:34
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountMapper accountMapper;

    @Transactional
    @Override
    public boolean debit(String userId, int money) {
        log.info("=============冻结用户账户余额=================");
        log.info("当前 XID: {}", RootContext.getXID());

        checkBalance(userId, money);

        log.info("开始冻结用户 {} 余额", userId);
        //冻结金额
        Integer record = accountMapper.freezeBalance(userId, money);

        log.info("冻结用户 {} 余额结果:{}", userId, record > 0 ? "操作成功" : "扣减余额失败");
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("=============扣减冻结金额=================");

        String userId = actionContext.getActionContext("userId").toString();
        int money = (int) actionContext.getActionContext("money");
        //扣减冻结金额
        accountMapper.reduceFreezeBalance(userId, money);

        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("=============解冻金额=================");

        String userId = actionContext.getActionContext("userId").toString();
        int money = (int) actionContext.getActionContext("money");
        //解冻金额
        accountMapper.unfreezeBalance(userId, money);

        return true;
    }

    private void checkBalance(String userId, int money) {
        log.info("检查用户 {} 余额", userId);
        Account account = accountMapper.selectByUserId(userId);

        if (account.getMoney() < money) {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, account.getMoney());
            throw new RuntimeException("余额不足");
        }

    }
}
