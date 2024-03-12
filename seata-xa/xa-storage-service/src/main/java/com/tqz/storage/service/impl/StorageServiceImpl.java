package com.tqz.storage.service.impl;

import com.tqz.datasource.entity.Storage;
import com.tqz.datasource.mapper.StorageMapper;
import com.tqz.storage.service.StorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存实现类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 16:51
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Transactional
    @Override
    public void deduct(String commodityCode, int count) {
        log.info("=============扣减库存=================");
        log.info("当前 XID: {}", RootContext.getXID());
        // 检查库存
        checkStock(commodityCode, count);

        log.info("开始扣减 {} 库存", commodityCode);
        Integer record = storageMapper.reduceStorage(commodityCode, count);
        log.info("扣减 {} 库存结果:{}", commodityCode, record > 0 ? "操作成功" : "扣减库存失败");
    }

    private void checkStock(String commodityCode, int count) {

        log.info("检查 {} 库存", commodityCode);
        Storage storage = storageMapper.findByCommodityCode(commodityCode);

        if (storage.getCount() < count) {
            log.warn("{} 库存不足，当前库存:{}", commodityCode, count);
            throw new RuntimeException("库存不足");
        }

    }

}
