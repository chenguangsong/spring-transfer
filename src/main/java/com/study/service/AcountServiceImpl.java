package com.study.service;

import com.study.annotation.Autowired;
import com.study.annotation.Service;
import com.study.annotation.Transactional;
import com.study.dao.IAccountDao;
import com.study.pojo.Account;

/**
 * @ClassName AcountServiceImpl
 * @Description 转账业务类，赋值事务控制及业务处理
 * @Author chenguang
 * @Date 2020-05-05 16:56
 * @Version 1.0
 **/
@Transactional
@Service
public class AcountServiceImpl implements IAccountService{


    @Autowired
    private IAccountDao dao;


    public void updateAccount(String fromCardNo,String toCardNo,int money) throws Exception {

        Account fromAccount = dao.queryAccount(fromCardNo);
        System.out.println(fromAccount);
        Account toAccount = dao.queryAccount(toCardNo);
        System.out.println(toAccount);

        dao.updateAccount(fromCardNo,fromAccount.getMoney() - money);
        double a = 1/0;
        dao.updateAccount(toCardNo,toAccount.getMoney() + money);

        System.out.println("================转账后================");
        Account fromAccount1 = dao.queryAccount(fromCardNo);
        System.out.println(fromAccount1);
        Account toAccount1 = dao.queryAccount(toCardNo);
        System.out.println(toAccount1);
    }
}
