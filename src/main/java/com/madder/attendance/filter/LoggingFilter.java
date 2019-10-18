/*
 * @Copy.Right (c)2018 greatgas.cn
 * 
 * @Date 2018年7月25日 上午9:31:38
 */
package com.madder.attendance.filter;

import com.madder.attendance.Constants;
import com.madder.attendance.utils.HttpRequestUtil;
import com.madder.attendance.utils.SelfRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 
 * @author yinshijie
 */
@WebFilter(filterName = "loggingFilter", urlPatterns = "/*")
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 默认添加msgId属性
        Object msgIdObj = request.getAttribute(Constants.DEFAULT_MESSAGE_ID_FIELD_NAME);
        String msgId = null;
        if (msgIdObj == null) {
            msgId = UUID.randomUUID().toString().replaceAll("-", "");
            request.setAttribute(Constants.DEFAULT_MESSAGE_ID_FIELD_NAME, msgId);
        } else {
            msgId = msgIdObj.toString();
        }

        // 请求源IP
        String ip = HttpRequestUtil.getRequestIp(request);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        // request日志处理
        String contentType = request.getContentType();
        if (!request.getMethod().equalsIgnoreCase("GET")
                && (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType)
                        || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(contentType)
                        || "application/json; charset=UTF-8".equalsIgnoreCase(contentType))) {
            SelfRequestWrapper requestWrapper = new SelfRequestWrapper(request);
            byte[] contentDataBytes = requestWrapper.getContentData();
            String contentData = new String(contentDataBytes, Constants.DEFAULT_CHARSET);
            LOGGER.info("{}({}) [{}({})]:{}", msgId, ip, request.getServletPath(), request.getMethod(), contentData);
            filterChain.doFilter(requestWrapper, responseWrapper);
        } else {
            LOGGER.info("{}({}) [{}({})]", msgId, ip, request.getServletPath(), request.getMethod());
            filterChain.doFilter(request, responseWrapper);
        }


        // response日志处理+
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(response.getContentType())
                || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(response.getContentType())) {
            byte[] contentData = responseWrapper.getContentAsByteArray();
            // GZIP处理
            /*String headEncoding = response.getHeader("Content-Encoding");
            byte[] ungzipContentData = null;
            if (headEncoding != null && (headEncoding.indexOf("gzip") >= 0)) { // 客户端支持gzip
                ungzipContentData = GZipUtil.unCompress(contentData);
            }
            String content = new String((ungzipContentData != null ? ungzipContentData : contentData), Constants.DEFAULT_CHARSET);*/
            String content = new String(contentData, Constants.DEFAULT_CHARSET);

            LOGGER.info("{} [{}({})]:[{}]", msgId, request.getServletPath(),
                    responseWrapper.getHeader("Content-Encoding"), content);
        } else {
            LOGGER.info("{} [{}({})]:[{}]", msgId, request.getServletPath(), response.getHeader("Content-Encoding"),
                    response.getContentType());
        }
        responseWrapper.copyBodyToResponse();
    }
}
