package com.study.ustils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @ClassName DataSourceUtils
 * @Description 获取数据源信息
 * @Author chenguang
 * @Date 2020-05-05 17:09
 * @Version 1.0
 **/
public class DataSourceUtils {

    private DataSourceUtils(){}

    private static DruidDataSource druidDataSource = new DruidDataSource();

    static {
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql:///LAGOU_STUDY?useSSL=false");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root@123");
    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }
}
