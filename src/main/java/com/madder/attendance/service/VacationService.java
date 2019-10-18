package com.madder.attendance.service;

import com.madder.attendance.bean.TVacation;

import java.util.List;

/**
 * 请假记录service
 * @Author wangqian
 * @Date 2019-09-12 11:45
 **/

public interface VacationService {

    /**
     * 新增请假记录
     * @param tVacation
     */
    void insertVacation(TVacation tVacation);

    /**
     * 根据条件查询请假记录
     * @param params
     * @return
     */
    List<TVacation> findVacationList(TVacation params);
}
