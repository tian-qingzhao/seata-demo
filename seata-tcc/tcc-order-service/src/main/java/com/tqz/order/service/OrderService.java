package com.tqz.order.service;

import com.tqz.datasource.entity.Order;
import com.tqz.order.vo.OrderVo;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 订单接口，使用 {@link LocalTCC} 注解向 TC 注册分支事务
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/12 17:35
 */
@LocalTCC
public interface OrderService {

    /**
     * TCC的try方法：保存订单信息，状态为支付中
     * <p>
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     * name = 该tcc的bean名称,全局唯一
     * commitMethod = commit 为二阶段确认方法
     * rollbackMethod = rollback 为二阶段取消方法
     * BusinessActionContextParameter注解 传递参数到二阶段中
     * useTCCFence seata1.5.1的新特性，用于解决TCC幂等，悬挂，空回滚问题，需增加日志表tcc_fence_log
     */
    @TwoPhaseBusinessAction(name = "prepareSaveOrder",
            commitMethod = "commit",
            rollbackMethod = "rollback",
            useTCCFence = true)
    Order prepareSaveOrder(OrderVo orderVo,
                           @BusinessActionContextParameter(paramName = "orderId") Long orderId);

    /**
     * TCC的confirm方法：订单状态改为支付成功
     * <p>
     * 二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * TCC的cancel方法：订单状态改为支付失败
     * 二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}