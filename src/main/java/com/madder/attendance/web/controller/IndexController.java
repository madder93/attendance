package com.madder.attendance.web.controller;

import com.madder.attendance.Constants;
import com.madder.attendance.bean.TUser;
import com.madder.attendance.bean.vo.UserSessionVo;
import com.madder.attendance.common.ResponseData;
import com.madder.attendance.common.UserSessionUtil;
import com.madder.attendance.common.Utils;
import com.madder.attendance.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wangqian
 * @Date 2019-09-10 11:17
 **/

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * 跳转首页
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "index";
    }

    /**
     * 跳转登录
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "login";
    }

    /**
     * 登录
     * @param request
     * @param response
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(HttpServletRequest request, HttpServletResponse response, TUser user){
        ResponseData responseData = ResponseData.SUCCESS();

        try {
            if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())){
                responseData = ResponseData.SYSTEM_ERROR();
                responseData.setDescribe("姓名或密码不能为空");
                return responseData;
            }

            //解密密码
            String password = new String(Base64Utils.decode(user.getPassword().getBytes("UTF-8")), "UTF-8");
            user.setPassword(password);

            String sessionId = UserSessionUtil.getUserSessionId(request);
            UserSessionVo userSessionVo = userService.login(user, sessionId);
            if(userSessionVo != null){   //登录成功把session存到cookie
                UserSessionUtil.putCookie(request, response, Constants.SESSION_COOKIE_NAME, sessionId, false);
            }else{   //跳转回登录页
                responseData = ResponseData.SYSTEM_ERROR();
                responseData.setDescribe("登录失败！");
                return responseData;
            }

        }catch (Exception e){
            e.printStackTrace();
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("登录报错:" + e.getMessage());
            return responseData;
        }

        return responseData;
    }

    /**
     * 跳转修改密码
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toResetPwd", method = RequestMethod.GET)
    public String toResetPwd(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        return "resetPwd";
    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @param userName
     * @param password
     * @param passwordAgain
     * @return
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updatePwd(HttpServletRequest request, HttpServletResponse response, String userName, String password, String passwordAgain){
        ResponseData responseData = ResponseData.SUCCESS();

        try {
            if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(passwordAgain)){
                responseData = ResponseData.PARAMS_ERROR();
                responseData.setDescribe("参数不能为空");
                return responseData;
            }

            //解密密码
            String vpassword = new String(Base64Utils.decode(password.getBytes("UTF-8")), "UTF-8");
            String vsurepassword = new String(Base64Utils.decode(passwordAgain.getBytes("UTF-8")), "UTF-8");
            if(!StringUtils.equals(vpassword, vsurepassword)){
                responseData = ResponseData.SYSTEM_ERROR();
                responseData.setDescribe("两次输入的密码不一致！");
                return responseData;
            }

            userService.updatePwd(userName, vpassword);
        }catch (Exception e){
            e.printStackTrace();
            responseData = ResponseData.SYSTEM_ERROR();
            responseData.setDescribe("修改密码报错:" + e.getMessage());
            return responseData;
        }

        return responseData;
    }

    @RequestMapping(value = "/getMd5", method = RequestMethod.GET)
    public String getMd5(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.addAttribute("md5", Utils.getMd5Str("123456"));
        return "util";
    }
}
