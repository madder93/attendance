package com.madder.attendance.service.impl;

import com.madder.attendance.bean.TVacation;
import com.madder.attendance.dao.VacationDao;
import com.madder.attendance.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请假记录service实现类
 * @Author wangqian
 * @Date 2019-09-17 16:33
 **/

@Service("vacationService")
public class VacationServiceImpl implements VacationService {

    @Autowired
    private VacationDao vacationDao;

    @Override
    public void insertVacation(TVacation tVacation) {
        vacationDao.insert(tVacation);
    }

    @Override
    public List<TVacation> findVacationList(TVacation params) {
        return vacationDao.findVacationList(params);
    }
}
