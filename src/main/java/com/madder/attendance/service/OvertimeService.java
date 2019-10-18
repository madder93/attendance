package com.madder.attendance.service;

import com.madder.attendance.bean.TOvertime;
import com.madder.attendance.bean.vo.OvertimeVo;

import java.util.List;
import java.util.Map;

/**
 * 加班记录service
 * @Author wangqian
 * @Date 2019-09-12 11:45
 **/

public interface OvertimeService {

    /**
     * 新增加班记录
     * @param tOvertime
     */
    void insertOvertime(TOvertime tOvertime);

    /**
     * 根据条件查询加班记录
     * @param params
     * @return
     */
    List<TOvertime> findOvertimeList(TOvertime params);

    /**
     * 根据条件查询所有加班/请假记录
     * @param params
     * @return
     */
    List<OvertimeVo> findAllRecordList(Map<String, Object> params);
}
