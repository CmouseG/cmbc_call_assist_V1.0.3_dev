package com.guiji.fsagent.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestHttpUtil {
    public static void setTimeout(long connectionTimeout, long socketTimeout){
        Unirest.setTimeouts(connectionTimeout, socketTimeout);
    }

    /**
     * 发起Post请求，内容类型为json
     * @param url
     * @param jsonBody
     * @return
     * @throws UnirestException
     */
    public static String post(String url, String jsonBody) throws Exception {
        HttpResponse<String> response = Unirest.post(url)
                .header("content-type", "application/json")
                .header("cache-control", "no-cache")
                .body(jsonBody)
                .asString();
        return response.getBody();
    }

    /**
     * 发起Put请求，内容类型为json
     * @param url
     * @param jsonBody
     * @return
     * @throws UnirestException
     */
    public static String put(String url, String jsonBody) throws Exception {
        HttpResponse<String> response = Unirest.put(url)
                .header("content-type", "application/json")
                .header("cache-control", "no-cache")
                .body(jsonBody)
                .asString();
        return response.getBody();
    }

    /**
     * 发起Get请求
     * @param url
     * @return
     * @throws UnirestException
     */
    public static String get(String url) throws Exception {
        HttpResponse<String> response = Unirest.get(url)
                .header("content-type", "application/json")
                .header("cache-control", "no-cache")
                .asString();
        return response.getBody();
    }

    /**
     * 发起Delete请求
     * @param url
     * @return
     * @throws UnirestException
     */
    public static String delete(String url) throws Exception {
        HttpResponse<String> response = Unirest.delete(url)
                .header("content-type", "application/json")
                .header("cache-control", "no-cache")
                .asString();
        return response.getBody();
    }

    /**
     * 发起Delete请求
     * @param url
     * @return
     * @throws UnirestException
     */
    public static String deleteWithBody(String url, String jsonBody) throws Exception {
        HttpResponse<String> response = Unirest.delete(url)
                .header("content-type", "application/json")
                .header("cache-control", "no-cache")
                .body(jsonBody)
                .asString();
        return response.getBody();
    }
}
