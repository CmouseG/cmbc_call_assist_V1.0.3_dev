package com.guiji.component.client.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.guiji.component.client.util.HttpHelper;

/**
* @ClassName: BodyReaderHttpServletRequestWrapper
* @Description: 重写BodyReaderHttpServletRequestWrapper，用于在从request中取出信息流（request.getInputStream()）
* 后，重新放入请求中，否则filter中处理后，后续的controler无法获取输入流
* @author: weiyunbo
* @date 2018年7月17日 下午6:31:39
* @version V1.0
*/
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper{

	private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = HttpHelper.getPostBodyString(request).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
