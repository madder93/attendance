package com.madder.attendance.bean;

import java.io.Serializable;

/**
 * 用户表
 * @Author wangqian
 * @Date 2019-09-17 17:16
 **/

public class TUser implements Serializable {

    private static final long serialVersionUID = -1094421460596538277L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 员工编号
     */
    private String userNo;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
