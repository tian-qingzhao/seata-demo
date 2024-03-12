package com.tqz.seata.product.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Product实体类
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:40
 */
@Data
@TableName("t_product")
public class Product {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    private String productCode;

    private String productName;

    private Integer count;

    private BigDecimal price;
}
