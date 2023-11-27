package com.tqz.seata.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>order实体类
 *
 * @author tianqingzhao
 * @since 2021/7/12 17:02
 */
@Data
@TableName("t_order")
public class Order {

    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;
    private String accountCode;
    private String productCode;
    private Integer count;
    private BigDecimal amount;
}
