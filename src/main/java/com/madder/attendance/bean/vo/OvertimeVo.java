package com.madder.attendance.bean.vo;

import com.madder.attendance.bean.TOvertime;

/**
 * @Description 加班记录vo
 * @Author wangqian
 * @Date 2019-10-15 10:14
 */

public class OvertimeVo extends TOvertime {

    private static final long serialVersionUID = -5509826754664393809L;

    /**
     * 员工编号
     */
    private String userNo;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 类型  01：加班  02：请假
     */
    private String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
