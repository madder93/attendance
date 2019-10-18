package com.madder.attendance.service;

import com.madder.attendance.bean.TUser;
import com.madder.attendance.bean.vo.UserSessionVo;

/**
 * 用户service
 * @Author wangqian
 * @Date 2019-09-17 17:25
 **/

public interface UserService {

    /**
     * 登录
     * @param tUser
     */
    UserSessionVo login(TUser tUser, String sessionId) throws Exception;

    /**
     * 根据sessionId获取sessionVo
     * @param sessionId
     * @return
     */
    UserSessionVo getSession(String sessionId);

    /**
     * 修改密码
     * @param userName
     * @param password
     */
    void updatePwd(String userName, String password) throws Exception;
}
