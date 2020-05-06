package com.study.ustils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName ConnectionUtils
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-06 15:57
 * @Version 1.0
 **/
public class ConnectionUtils {

    //存放当前线程的Connection对象
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    //私有化构造器，禁止外部创建实例
    private ConnectionUtils(){}

    public static synchronized  Connection getConnect() throws SQLException {
        if (threadLocal.get() == null){
            Connection connection =DataSourceUtils.getInstance().getConnection();
            threadLocal.set(connection);
        }
        return threadLocal.get();
    }

}
