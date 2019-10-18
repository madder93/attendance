package com.madder.attendance;

import java.nio.charset.Charset;

/**
 * 常量枚举类
 * @Author wangqian
 * @Date 2019-04-30 9:49
 **/

public class Constants {

    /**
     * 日志拦截类型
     */
    public static final String DEFAULT_MESSAGE_ID_FIELD_NAME = "sMsgId";
    public static final String DECODE_TYPE_NONE = "0";
    public static final String DECODE_TYPE_AES = "1";
    public static final String DECODE_TYPE_BASE64 = "2";
    public static final String REQUEST_PARAM_APPTOKEN = "appToken";
    public static final String REQUEST_PARAM_TIMESTAMP = "timestamp";
    public static final String REQUEST_PARAM_NONCE = "nonce";
    public static final String REQUEST_PARAM_SIGNATURE = "signature";
    public static final String DEVICE_TYPE_ANDROID = "01";
    public static final String DEVICE_TYPE_IOS = "02";
    public static final String DEVICE_TYPE_WINDOWSPHONE = "03";
    public static final String DEVICE_TYPE_UNKOWN = "99";

    //字符编码
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    //sessionId存在cookie中的名字
    public static final String SESSION_COOKIE_NAME = "attendance_cookie_session_id";

    //sessionId存在header中的名字
    public static final String SESSION_HEADER_NAME = "attendance_header_session_id";

    //sessionId存在redis中的前缀
    public static final String SESSION_REDIS_PREIX = "attendance_session_id";

}
