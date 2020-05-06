package com.study.dao;

import com.study.pojo.Account;

/**
 * @ClassName IAccountDao
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-05 16:57
 * @Version 1.0
 **/
public interface IAccountDao {

    //查询账户信息
    Account queryAccount(String accountNo) throws Exception;

    //更新账户信息
    void updateAccount(String cardNo,int money) throws Exception;

}
