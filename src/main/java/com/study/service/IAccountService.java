package com.study.service;

/**
 * @ClassName IAccountService
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-05 16:56
 * @Version 1.0
 **/
public interface IAccountService {


    //更新账户信息
    void updateAccount(String fromUserId,String toUserId,int money) throws Exception;

}
