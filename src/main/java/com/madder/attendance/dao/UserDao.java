package com.madder.attendance.dao;

import com.madder.attendance.bean.TUser;

/**
 * 用户dao
 * @Author wangqian
 * @Date 2019-09-17 17:28
 **/

public interface UserDao {

    /**
     * 根据条件查询用户
     * @param tUser
     * @return
     */
    TUser getUserByCond(TUser tUser);

    /**
     * 修改密码
     * @param tUser
     */
    void updatePwd(TUser tUser);
}
