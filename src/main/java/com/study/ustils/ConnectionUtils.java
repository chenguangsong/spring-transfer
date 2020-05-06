package com.study.ustils;

import java.sql.Connection;

/**
 * @ClassName ConnectionUtils
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-06 15:57
 * @Version 1.0
 **/
public class ConnectionUtils {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    private ConnectionUtils(){}

    public static synchronized  Connection getConnect() throws Exception{
        if (threadLocal.get() == null){
            Connection connection =DataSourceUtils.getDataSource().getConnection();
            threadLocal.set(connection);
        }
        return threadLocal.get();
    }

}
