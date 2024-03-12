package com.tqz.seata.account.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>account实体类
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:57
 */
@Data
@TableName("t_account")
public class Account {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String accountCode;
    private String accountName;
    private BigDecimal amount;
}
