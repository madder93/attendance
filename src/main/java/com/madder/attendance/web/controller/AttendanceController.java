package com.madder.attendance.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.madder.attendance.bean.vo.OvertimeVo;
import com.madder.attendance.common.ResponseData;
import com.madder.attendance.service.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangqian
 * @Date 2019-10-17 14:01
 */

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private OvertimeService overtimeService;

    /**
     * 跳转所有加班/请假记录页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAllRecord", method = RequestMethod.GET)
    public String toAllRecord(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "attendanceList";
    }

    /**
     * 根据条件查询所有加班/请假记录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/findAllRecordList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData findAllRecordList(HttpServletRequest request, HttpServletResponse response,
                                          String params){
        ResponseData responseData = ResponseData.SUCCESS();
        try{
            Map<String, Object> map = (Map<String, Object>) JSONUtils.parse(params);
            List<OvertimeVo> list = overtimeService.findAllRecordList(map);
            responseData.addResultObject("list", list);
        }catch (Exception e){
            e.printStackTrace();
            //logger.error("新增加班记录报错：" + e.getMessage(), e);
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("查询记录报错" + e.getMessage());
            return responseData;
        }

        return responseData;
    }
}
