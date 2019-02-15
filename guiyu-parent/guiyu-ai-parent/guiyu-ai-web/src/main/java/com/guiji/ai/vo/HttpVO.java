package com.guiji.ai.vo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import lombok.Data;

@Data
public class HttpVO
{
	private HttpEntity httpEntity;
	private CloseableHttpResponse httpResponse;
	private CloseableHttpClient httpClient;
}
