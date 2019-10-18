package com.madder.attendance.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 加班记录表
 * @Author wangqian
 * @Date 2019-09-12 11:32
 **/

public class TOvertime implements Serializable {

    private static final long serialVersionUID = -7357930951618506984L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date endDate;

    /**
     * 加班类型   01:公休日加班  02：节假日加班  03：工作日晚上加班
     */
    private String type;

    /**
     * 天数
     */
    private Float days;

    /**
     * 是否算调休  00：否  01：是
     */
    private String ifChange;

    /**
     * 调休天数
     */
    private Float changeDays;

    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getDays() {
        return days;
    }

    public void setDays(Float days) {
        this.days = days;
    }

    public String getIfChange() {
        return ifChange;
    }

    public void setIfChange(String ifChange) {
        this.ifChange = ifChange;
    }

    public Float getChangeDays() {
        return changeDays;
    }

    public void setChangeDays(Float changeDays) {
        this.changeDays = changeDays;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
