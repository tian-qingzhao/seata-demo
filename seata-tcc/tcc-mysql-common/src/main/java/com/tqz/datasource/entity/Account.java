package com.tqz.datasource.entity;

import lombok.Data;


/**
 * 账户实体类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 18:07
 */
@Data
public class Account {
    private Integer id;
    
    private String userId;
    
    private Integer money;
}
