package com.madder.attendance.interceptor;

import com.madder.attendance.bean.vo.UserSessionVo;
import com.madder.attendance.common.UserSessionUtil;
import com.madder.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wangqian
 * @Date 2019-09-18 16:55
 **/

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = UserSessionUtil.getUserSessionId(request);
        UserSessionVo userSessionVo = userService.getSession(sessionId);
        if(userSessionVo == null){  //没登录
            response.sendRedirect(request.getContextPath() + "/index/toLogin");
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
