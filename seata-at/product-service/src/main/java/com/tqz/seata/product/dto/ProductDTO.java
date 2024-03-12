package com.tqz.seata.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>接口传输层
 *
 * @author tianqingzhao
 * @since 2021/7/13 9:42
 */
@Data
@ApiModel(value = "产品封装类ProductDTO", description = "产品相关信息封装，用于接口传参")
public class ProductDTO {

    @ApiModelProperty(value = "产品主键")
    private Integer id;
    @ApiModelProperty(value = "产品编码")
    private String productCode;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "数量")
    private Integer count;
    @ApiModelProperty(value = "单价")
    private BigDecimal price;
}
