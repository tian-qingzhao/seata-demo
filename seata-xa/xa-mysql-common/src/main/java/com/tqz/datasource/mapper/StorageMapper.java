package com.tqz.datasource.mapper;

import com.tqz.datasource.entity.Storage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 库存mapper接口
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 18:05
 */
@Repository
public interface StorageMapper {

    /**
     * 获取库存
     *
     * @param commodityCode 商品编号
     * @return
     */
    @Select("SELECT id,commodity_code,count FROM storage_tbl WHERE commodity_code = #{commodityCode}")
    Storage findByCommodityCode(@Param("commodityCode") String commodityCode);

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编号
     * @param count         要扣减的库存
     * @return
     */
    @Update("UPDATE storage_tbl SET count = count - #{count} WHERE commodity_code = #{commodityCode}")
    int reduceStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);

}
