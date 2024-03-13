package com.tqz.jta;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.*;

/**
 * JTA（Java Transaction API）
 * 即Java的事务API，基于XA实现，也就是RM需要支持XA，所以也有JTA(XA)的说法，JTA仅定义了接口。
 * 主要包括 javax.sql.XADataResource、javax.sql.XAConnection、
 * javax.sql.XAException、javax.transaction.xa.XAResource、javax.transaction.Xid。
 *
 * <p>XA协议与JTA
 * XA协议:
 * XA协议是处理分布式事务的一种协议。是数据库层面的协议。想要使用XA协议解决分布式事务问题，
 * 前提条件是数据库必须支持XA协议。常见的mysql数据库和oracle数据库都支持XA协议。
 * JTA:
 * JTA是java定义的解决分布式事务的一个规范接口。其类似于JDBC定义了连接数据库接口一样。
 * JTA是XA协议在java上的实现。意思就是想在java程序使用数据库的XA协议完成分布式事务，必须遵守JTA定义的规范。
 * atomikos:
 * atomikos是遵循JTA规范实现了XA协议的一个java程序。我们直接引入相应jar包，
 * 就可以通过atomikos来使用XA协议的分布式事务来处理数据。
 * 首先，搞懂上述三者的关系，有助于理解下面的知识。接下来，详细介绍一下XA协议。
 * X/Open组织定义了分布式事务处理模型DTP模型，该模型定义了如下几个角色:
 * 应用程序AP:我们的项目
 * 事务协调器TM:全局事务管理者
 * 数据库RM
 * 通信资源管理器(CRM):是TM和RM间通信的中间件。
 * 在该模型中，一个分布式事务™可以分成多个本地事务，运行在不同的AP和RM上,每个本地事务都由自己的ACID。
 * 但是全局事务必须保证每个本地事务都是成功的，若有一个本地事务失败，则其他本地事务都需要回滚。
 * 这就需要CRM来通知各个本地事务，同步事务执行的状态。
 * 由上述可知，事务协调器需要与每个本地事务通信，然后再通知每个本地事务是提交还是回滚。
 * 那么，如果每个本地事务所对应的数据库不一致怎么办呢？比如A的节点使用了mysql数据库，B节点使用了oracle数据库。
 * 这就需要有一个统一的通信规则，无论哪个数据库厂商，只要完成DTP模型，都要按照这个规则来与事务管理器进行通信。这个规则，就是XA协议。
 * 所以,XA协议是数据库与事务管理器直接的通信规则。
 *
 * @author <a href="https://github.com/tian-qingzhao">tianqingzhao</a>
 * @since 2024/3/13 9:55
 */
public class MysqlXADemo {

    public static void main(String[] args) throws SQLException {
        //true表示打印XA语句,，用于调试
        boolean logXaCommands = true;
        // 获得资源管理器操作接口实例 RM1
        Connection conn1 = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/seata-xa-order", "root", "123456");
        XAConnection xaConn1 = new MysqlXAConnection(
                (com.mysql.jdbc.Connection) conn1, logXaCommands);
        XAResource rm1 = xaConn1.getXAResource();

        // 获得资源管理器操作接口实例 RM2
        Connection conn2 = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/seata-xa-storage", "root", "123456");
        XAConnection xaConn2 = new MysqlXAConnection(
                (com.mysql.jdbc.Connection) conn2, logXaCommands);
        XAResource rm2 = xaConn2.getXAResource();

        // AP请求TM执行一个分布式事务，TM生成全局事务id
        byte[] gtrid = "globalTransactionId".getBytes();
        int formatId = 1;
        try {

            // ==============分别执行RM1和RM2上的事务分支====================
            // TM生成rm1上的事务分支id
            byte[] bqual1 = "b00001".getBytes();
            Xid xid1 = new MysqlXid(gtrid, bqual1, formatId);
            // 执行rm1上的事务分支
            rm1.start(xid1, XAResource.TMNOFLAGS);//One of TMNOFLAGS, TMJOIN, or TMRESUME.
            PreparedStatement ps1 = conn1.prepareStatement(
                    "INSERT into order_tbl(user_id,commodity_code,count,money,status) VALUES (1001,2001,2,10,1)");
            ps1.execute();
            int updateCount1 = ps1.getUpdateCount();
            rm1.end(xid1, XAResource.TMSUCCESS);

            // TM生成rm2上的事务分支id
            byte[] bqual2 = "brandTransactionId".getBytes();
            Xid xid2 = new MysqlXid(gtrid, bqual2, formatId);
            // 执行rm2上的事务分支
            rm2.start(xid2, XAResource.TMNOFLAGS);
            PreparedStatement ps2 = conn2.prepareStatement(
                    "update storage_tbl set count=count-1 where commodity_code=001");
            ps2.execute();
            int updateCount2 = ps2.getUpdateCount();
            rm2.end(xid2, XAResource.TMSUCCESS);

            // e===================两阶段提交================================
            //            // phas1：询问所有的RM 准备提交事务分支
            int rm1Prepare = rm1.prepare(xid1);
            int rm2Prepare = rm2.prepare(xid2);
            // phase2：提交所有事务分支
            boolean onePhase = false;
            //TM判断有2个事务分支，所以不能优化为一阶段提交
            if (rm1Prepare == XAResource.XA_OK && rm2Prepare == XAResource.XA_OK &&
                    updateCount1 > 0 && updateCount2 > 0) {
                //所有事务分支都prepare成功，提交所有事务分支
                rm1.commit(xid1, onePhase);
                rm2.commit(xid2, onePhase);
            } else {
                //如果有事务分支没有成功，则回滚
                rm1.rollback(xid1);
                rm2.rollback(xid2);
            }
        } catch (XAException e) {
            // 如果出现异常，也要进行回滚
            e.printStackTrace();
        }
    }
}