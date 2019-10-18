package com.madder.attendance.common;

import com.madder.attendance.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author wangqian
 * @Date 2019-09-18 8:40
 **/

public class UserSessionUtil {

    /**
     * 设置sessionId到cookie中
     * @param request
     * @param response
     * @param cookieName
     * @param sessionId
     * @param isExipre
     */
    public static final void putCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String sessionId, boolean isExipre){
        Cookie cookie = new Cookie(cookieName, sessionId);
        cookie.setPath("/");    //可在同一应用服务器内共享方法  正常的cookie只能在一个应用中共享,即一个cookie只能由创建它的应用获得
        cookie.setHttpOnly(false);  //设置HttpOnly=true的cookie不能被js获取到，无法用document.cookie打出cookie的内容

        String url = request.getRequestURL().toString();
        Pattern domainPattern = Pattern.compile("^https?://([^/:]*)");
        Matcher domainMatcher = domainPattern.matcher(url);
        if(domainMatcher.find()){
            cookie.setDomain(domainMatcher.group(1));
        }
        if(isExipre){
            cookie.setMaxAge(0);   //从客户端电脑或浏览器内存中删除此cookie。
        }else{
            cookie.setMaxAge(-1);  //此cookie只是存储在浏览器内存里，只要关闭浏览器，此cookie就会消失。maxAge默认值为-1。
        }

        response.addCookie(cookie);
    }

    /**
     * 获取sessionId
     * @param request
     * @return
     */
    public static final String getUserSessionId(HttpServletRequest request){
        String sessionId = "";

        //从cookie中取sessionId
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if(StringUtils.equals(Constants.SESSION_COOKIE_NAME, cookie.getName())){
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        //如果cookie中没有，就从header里取
        if(StringUtils.isBlank(sessionId)){
            sessionId = request.getHeader(Constants.SESSION_HEADER_NAME);
        }

        //如果header里没有，就从session中取
        if(StringUtils.isBlank(sessionId)){
            sessionId = request.getRequestedSessionId();
        }

        return sessionId;
    }
}
