package com.madder.attendance.web.controller;

import com.madder.attendance.bean.TVacation;
import com.madder.attendance.bean.vo.UserSessionVo;
import com.madder.attendance.common.ResponseData;
import com.madder.attendance.common.UserSessionUtil;
import com.madder.attendance.service.UserService;
import com.madder.attendance.service.VacationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 请假记录控制层
 * @Author wangqian
 * @Date 2019-09-10 14:38
 **/

@Controller
@RequestMapping("/vacation")
public class VacationController {

    //private Logger logger = LoggerFactory.getLogger(VacationController.class);

    @Autowired
    private VacationService vacationService;

    @Autowired
    private UserService userService;

    /**
     * 跳转请假记录页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toVacation", method = RequestMethod.GET)
    public String toVacation(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "vacation";
    }

    /**
     * 新增请假记录
     * @param request
     * @param response
     * @param tVacation
     * @return
     */
    @RequestMapping(value = "/insertVacation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData insertVacation(HttpServletRequest request, HttpServletResponse response, TVacation tVacation){
        ResponseData responseData = ResponseData.SUCCESS();
        try{
            String sessionId = UserSessionUtil.getUserSessionId(request);
            UserSessionVo userSessionVo = userService.getSession(sessionId);

            tVacation.setUserId(userSessionVo.getUser().getUserId());
            tVacation.setCreateTime(new Date());
            vacationService.insertVacation(tVacation);
        }catch (Exception e){
            e.printStackTrace();
            //logger.error("新增请假记录报错：" + e.getMessage(), e);
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("新增请假记录报错" + e.getMessage());
            return responseData;
        }
        
        return responseData;
    }

    /**
     * 跳转请假记录页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toViewVacation", method = RequestMethod.GET)
    public String toViewVacation(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "viewVacation";
    }

    /**
     * 查询当前登录人当前月的加班记录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/findVacationList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData findVacationList(HttpServletRequest request, HttpServletResponse response){
        ResponseData responseData = ResponseData.SUCCESS();
        try{
            /*String sessionId = UserSessionUtil.getUserSessionId(request);
            UserSessionVo userSessionVo = userService.getSession(sessionId);
            Integer userId = userSessionVo.getUser().getUserId();*/

            TVacation params = new TVacation();
            params.setUserId(1);

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH,1);//1:本月第一天
            String day1= format.format(c.getTime());

            Calendar ca = Calendar.getInstance();
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));//1:本月最后一天
            String day2= format.format(ca.getTime());

            params.setStartDate(DateUtils.parseDate(day1, "yyyy-MM-dd"));
            params.setEndDate(DateUtils.parseDate(day2, "yyyy-MM-dd"));

            List<TVacation> list = vacationService.findVacationList(params);
            HashMap<String, Object> results = new HashMap<String, Object>();
            responseData.addResultObject("list", list);
        }catch (Exception e){
            e.printStackTrace();
            //logger.error("新增加班记录报错：" + e.getMessage(), e);
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("查询请假记录报错" + e.getMessage());
            return responseData;
        }

        return responseData;
    }
}
