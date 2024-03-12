package com.tqz.seata.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tqz.seata.account.po.Account;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>Account Dao层
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:56
 */
public interface AccountMapper extends BaseMapper<Account> {

    Account selectByCode(@Param("accountCode") String accountCode);

    int deleteByCode(@Param("accountCode") String accountCode);

    void increaseAmount(@Param("accountCode") String accountCode, @Param("amount")BigDecimal amount);
}
