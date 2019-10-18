package com.madder.attendance.web.controller;

import com.madder.attendance.bean.TOvertime;
import com.madder.attendance.bean.vo.UserSessionVo;
import com.madder.attendance.common.ResponseData;
import com.madder.attendance.common.UserSessionUtil;
import com.madder.attendance.service.OvertimeService;
import com.madder.attendance.service.UserService;
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
import java.util.*;

/**
 * 加班记录控制层
 * @Author wangqian
 * @Date 2019-09-10 14:38
 **/

@Controller
@RequestMapping("/overtime")
public class OvertimeController {

    //private Logger logger = LoggerFactory.getLogger(OvertimeController.class);

    @Autowired
    private OvertimeService overtimeService;

    @Autowired
    private UserService userService;

    /**
     * 跳转加班记录页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toOvertime", method = RequestMethod.GET)
    public String toOvertime(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "overtime";
    }

    /**
     * 新增加班记录
     * @param request
     * @param response
     * @param tOvertime
     * @return
     */
    @RequestMapping(value = "/insertOvertime", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData insertOvertime(HttpServletRequest request, HttpServletResponse response, TOvertime tOvertime){
        ResponseData responseData = ResponseData.SUCCESS();
        try{
            String sessionId = UserSessionUtil.getUserSessionId(request);
            UserSessionVo userSessionVo = userService.getSession(sessionId);

            tOvertime.setUserId(userSessionVo.getUser().getUserId());
            tOvertime.setCreateTime(new Date());
            overtimeService.insertOvertime(tOvertime);
        }catch (Exception e){
            e.printStackTrace();
            //logger.error("新增加班记录报错：" + e.getMessage(), e);
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("新增加班记录报错" + e.getMessage());
            return responseData;
        }
        
        return responseData;
    }

    /**
     * 跳转加班记录页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toViewOvertime", method = RequestMethod.GET)
    public String toViewOvertime(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "viewOvertime";
    }

    /**
     * 查询当前登录人当前月的加班记录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/findOvertimeList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData findOvertimeList(HttpServletRequest request, HttpServletResponse response){
        ResponseData responseData = ResponseData.SUCCESS();
        try{
            String sessionId = UserSessionUtil.getUserSessionId(request);
            UserSessionVo userSessionVo = userService.getSession(sessionId);
            Integer userId = userSessionVo.getUser().getUserId();
            //Integer userId = 1;

            TOvertime params = new TOvertime();
            params.setUserId(userId);

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

            List<TOvertime> list = overtimeService.findOvertimeList(params);
            HashMap<String, Object> results = new HashMap<String, Object>();
            responseData.addResultObject("list", list);
        }catch (Exception e){
            e.printStackTrace();
            //logger.error("新增加班记录报错：" + e.getMessage(), e);
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("查询加班记录报错" + e.getMessage());
            return responseData;
        }

        return responseData;
    }
}
