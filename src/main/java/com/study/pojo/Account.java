package com.study.pojo;

import java.io.Serializable;

/**
 * @ClassName Account
 * @Description 账户信息实体类
 * @Author chenguang
 * @Date 2020-05-05 16:53
 * @Version 1.0
 **/
public class Account implements Serializable {

    private String userId;

    private String cardNo;

    private int money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId='" + userId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", money=" + money +
                '}';
    }
}


