package com.madder.attendance.dao;

import com.madder.attendance.bean.TVacation;

import java.util.List;

/**
 * @Author wangqian
 * @Date 2019-09-12 11:31
 **/

public interface VacationDao {

    /**
     * 新增请假记录
     * @param tVacation
     */
    void insert(TVacation tVacation);

    /**
     * 根据条件查询请假记录
     * @param params
     * @return
     */
    List<TVacation> findVacationList(TVacation params);
}
