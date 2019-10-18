package com.madder.attendance.service.impl;

import com.madder.attendance.bean.TOvertime;
import com.madder.attendance.bean.vo.OvertimeVo;
import com.madder.attendance.dao.OvertimeDao;
import com.madder.attendance.service.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 加班记录service实现类
 * @Author wangqian
 * @Date 2019-09-12 13:48
 **/

@Service("overtimeService")
public class OvertimeServiceImpl implements OvertimeService {

    @Autowired
    private OvertimeDao overtimeDao;

    @Override
    public void insertOvertime(TOvertime tOvertime) {
        overtimeDao.insert(tOvertime);
    }

    @Override
    public List<TOvertime> findOvertimeList(TOvertime params) {
        return overtimeDao.findOvertimeList(params);
    }

    @Override
    public List<OvertimeVo> findAllRecordList(Map<String, Object> params) {
        return overtimeDao.findAllRecordList(params);
    }
}
