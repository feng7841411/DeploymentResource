package com.feng.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/13
 * Time: 14:35
 * Description:
 *
 * @author feng
 */
public interface RestTemplateService {


    /**
     * 通过post请求，接口传递jsonString
     *
     * @param targetUrl  目标URL
     * @param jsonString
     * @return
     */
    public ResponseEntity<String> postJsonString(String targetUrl, String jsonString);


    public ResponseEntity<String> sendGetRequest(String targetUrl);


    public ResponseEntity<String> postParamsRequest(String targetUrl, MultiValueMap<String, Object> map);


    public ResponseEntity<String> postWithRequestHeader(String targetUrl, HashMap<String, String> headerMap);

    public ResponseEntity<String> getWithRequestHeader(String targetUrl, HashMap<String, String> headerMap);


}
