package com.madder.attendance.bean.vo;

import com.madder.attendance.bean.TUser;

import java.io.Serializable;

/**
 * 用户session vo
 * @Author wangqian
 * @Date 2019-09-18 8:37
 **/

public class UserSessionVo implements Serializable {

    private static final long serialVersionUID = -1470647143706635063L;

    private String appId;

    private String sessionId;

    private TUser user;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }
}
