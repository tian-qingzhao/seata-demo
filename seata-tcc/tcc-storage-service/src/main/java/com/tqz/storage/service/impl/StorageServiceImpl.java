package com.tqz.storage.service.impl;

import com.tqz.datasource.entity.Storage;
import com.tqz.datasource.mapper.StorageMapper;
import com.tqz.storage.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存服务实现类
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 17:36
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Transactional
    @Override
    public boolean deduct(String commodityCode, int count) {
        log.info("=============冻结库存=================");
        log.info("当前 XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(commodityCode, count);

        log.info("开始冻结 {} 库存", commodityCode);
        //冻结库存
        Integer record = storageMapper.freezeStorage(commodityCode, count);
        log.info("冻结 {} 库存结果:{}", commodityCode, record > 0 ? "操作成功" : "扣减库存失败");
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("=============扣减冻结库存=================");

        String commodityCode = actionContext.getActionContext("commodityCode").toString();
        int count = (int) actionContext.getActionContext("count");
        //扣减冻结库存
        storageMapper.reduceFreezeStorage(commodityCode, count);

        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("=============解冻库存=================");

        String commodityCode = actionContext.getActionContext("commodityCode").toString();
        int count = (int) actionContext.getActionContext("count");
        //扣减冻结库存
        storageMapper.unfreezeStorage(commodityCode, count);

        return true;
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
