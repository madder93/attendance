package com.madder.attendance.dao;

import com.madder.attendance.bean.TOvertime;
import com.madder.attendance.bean.vo.OvertimeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author wangqian
 * @Date 2019-09-12 11:31
 **/

public interface OvertimeDao {

    /**
     * 新增加班记录
     * @param tOvertime
     */
    void insert(TOvertime tOvertime);

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
    List<OvertimeVo> findAllRecordList(@Param("params") Map<String, Object> params);
}
