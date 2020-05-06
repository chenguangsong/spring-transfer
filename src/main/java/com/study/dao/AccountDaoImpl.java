package com.study.dao;

import com.study.annotation.Repository;
import com.study.pojo.Account;
import com.study.ustils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AccountDaoImpl
 * @Description TODO
 * @Author songchenguang
 * @Date 2020-05-05 16:57
 * @Version 1.0
 **/
@Repository
public class AccountDaoImpl implements IAccountDao{

    public Account queryAccount(String accountNo) throws Exception {
        List<Account> list = new ArrayList<Account>();
        Connection connection = ConnectionUtils.getConnect();
        String sql = "select * from ACCOUNT where CARD_NO = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,accountNo);
        Account account = new Account();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String userId = resultSet.getString("USER_ID");
            String cardNo = resultSet.getString("CARD_NO");
            int money = resultSet.getInt("MONEY");
            account.setUserId(userId);
            account.setCardNo(cardNo);
            account.setMoney(money);
            list.add(account);
        }

        return account;

    }

    public void updateAccount(String cardNo, int money) throws Exception {
        Connection connection = ConnectionUtils.getConnect();
        String sql = "update ACCOUNT set MONEY = ? where CARD_NO = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,money);
        preparedStatement.setString(2,cardNo);
        preparedStatement.execute();
    }
}
