package com.tqz.datasource.mapper;

import com.tqz.datasource.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 账户mapper接口
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 18:05
 */
@Repository
public interface AccountMapper {

    /**
     * 查询账户
     *
     * @param userId
     * @return
     */
    @Select("select id, user_id, money from account_tbl WHERE user_id = #{userId}")
    Account selectByUserId(@Param("userId") String userId);

    /**
     * 扣减余额
     *
     * @param userId 用户id
     * @param money  要扣减的金额
     * @return
     */
    @Update("update account_tbl set money =money-#{money} where user_id = #{userId}")
    int reduceBalance(@Param("userId") String userId, @Param("money") Integer money);

}
