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

    //私有化构造函数，禁止外部创建
    private DataSourceUtils(){};

    private static DruidDataSource dataSource = new DruidDataSource();

    public  static DruidDataSource getDataSource (){
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///LAGOU_STUDY?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root@123");
        return dataSource;
    }
}
