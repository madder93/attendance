package com.madder.attendance.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 解析前端的数据
 * @Author wangqian
 * @Date 2019-04-30 10:47
 **/

public class SelfRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public SelfRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = this.readBytes(request.getReader(), "UTF-8");
    }

    public byte[] getContentData() {
        return this.body;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
        return new ServletInputStream() {
            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener listener) {
            }

            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    private byte[] readBytes(BufferedReader br, String encoding) throws IOException {
        String str = null;

        String retStr;
        for(retStr = ""; (str = br.readLine()) != null; retStr = retStr + str) {
            ;
        }

        return StringUtils.isNotBlank(retStr) ? retStr.getBytes(Charset.forName(encoding)) : null;
    }
}
