package com.tqz.datasource.entity;

import lombok.Data;

/**
 * 库存实体类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 18:04
 */
@Data
public class Storage {
    private Integer id;

    private String commodityCode;

    private Integer count;

}
