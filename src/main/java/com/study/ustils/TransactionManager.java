package com.study.ustils;

import java.sql.SQLException;

/**
 * @ClassName TransactionManager
 * @Description TODO
 * @Author chenguang
 * @Date 2020-05-06 20:52
 * @Version 1.0
 **/
public class TransactionManager {

    private TransactionManager(){};

    /**
    * @author chenguang
    * @Description //开启事务
    * @CreateDate 2020-05-06 20:53
    * @Param []
    * @return void
    **/
    public static void begin() throws SQLException {
        ConnectionUtils.getConnect().setAutoCommit(false);
    }

    /**
    * @author chenguang
    * @Description //提交事务
    * @CreateDate 2020-05-06 20:56
    * @Param []
    * @return void
    **/
    public static void commit() throws SQLException{
        ConnectionUtils.getConnect().commit();
    }

    /**
    * @author chenguang
    * @Description //事务回滚
    * @CreateDate 2020-05-06 20:57
    * @Param []
    * @return void
    **/
    public static void rollBack() throws SQLException {
        ConnectionUtils.getConnect().rollback();
    }

}
