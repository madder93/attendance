package com.madder.attendance.utils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * 网络请求工具类
 * @Author wangqian
 * @Date 2019-04-30 10:41
 **/

public class HttpRequestUtil {

    public HttpRequestUtil() {}

    public static String getRequestContent(HttpServletRequest httpServletRequest, String charSet) throws Exception {
        ServletInputStream localServletInputStream = null;

        try {
            localServletInputStream = httpServletRequest.getInputStream();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            byte[] arrayOfByte = new byte[4096];
            boolean var5 = true;

            int i;
            while((i = localServletInputStream.read(arrayOfByte, 0, 4096)) != -1) {
                localByteArrayOutputStream.write(arrayOfByte, 0, i);
            }

            arrayOfByte = null;
            return new String(localByteArrayOutputStream.toByteArray(), charSet);
        } catch (Exception var6) {
            throw new Exception(var6);
        }
    }

    public static Map<String, String[]> getParams(HttpServletRequest request) {
        return request.getParameterMap();
    }

    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            String[] ipList = ip.split(",");
            String[] var6 = ipList;
            int var5 = ipList.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                String tempIp = var6[var4];
                if (!tempIp.trim().equalsIgnoreCase("unknown")) {
                    ip = tempIp.trim();
                    break;
                }
            }
        } else {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
