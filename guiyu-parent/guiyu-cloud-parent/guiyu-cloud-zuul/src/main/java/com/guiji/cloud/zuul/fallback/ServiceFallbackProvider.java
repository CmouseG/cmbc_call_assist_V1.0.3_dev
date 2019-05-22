package com.guiji.cloud.zuul.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guiji.cloud.zuul.model.ErrorCode;
import com.guiji.cloud.zuul.model.Msg;

/**
 * Created by Administratorn 2017/12/26.
 * Time:10:12
 * ProjectName:guiyu-cloud-zuul
 */
@Component
public class ServiceFallbackProvider implements FallbackProvider {
	private static final Logger log = LoggerFactory.getLogger(ServiceFallbackProvider.class);

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
    	log.error(cause.getMessage());
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                //响应体
                Msg msg = new Msg();
                msg.setRspCode(ErrorCode.MICRO_SERVICE_UNAVAILABLE);
                msg.setRspMsg("微服务不可用，请稍后再试");
                ObjectMapper objectMapper = new ObjectMapper();
                String content = objectMapper.writeValueAsString(msg);
                return new ByteArrayInputStream(content.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }

    @Override
    public String getRoute() {
        //表明是为哪个微服务提供回退，"*"全部
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return null;
    }
}
