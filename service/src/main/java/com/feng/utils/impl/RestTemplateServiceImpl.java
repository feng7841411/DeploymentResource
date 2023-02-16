package com.feng.utils.impl;


import com.feng.utils.RestTemplateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    private final RestTemplate restTemplate;

    private static final Logger logger = LogManager.getLogger(RestTemplateServiceImpl.class);


    public RestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public ResponseEntity<String> postJsonString(String targetUrl, String jsonString) {
        logger.info("工具类调用：postJsonString");
        logger.info("目标接口：" + targetUrl);
        logger.info("待发送JSONString：" + jsonString);
        // 设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // 写入
        HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);
        // 发出请求
        return restTemplate.postForEntity(targetUrl, entity, String.class);
    }

    @Override
    public ResponseEntity<String> sendGetRequest(String targetUrl) {
        ResponseEntity<String> exchange = restTemplate.exchange(targetUrl, HttpMethod.GET, null, String.class);
        return exchange;
    }

    @Override
    public ResponseEntity<String> postParamsRequest(String targetUrl, MultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(targetUrl, request, String.class);
        return response;
    }

    @Override
    public ResponseEntity<String> postWithRequestHeader(String targetUrl, HashMap<String, String> headerMap) {
        logger.info("postWithRequestHeader工具类被调用");
        // 1.设置请求头参数
        HttpHeaders headers = new HttpHeaders();
        for (String headerKey : headerMap.keySet()) {
            headers.add(headerKey, headerMap.get(headerKey));
        }
        // 2.new一个请求体，不需要携带就是new了一个空壳子
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        // 3. 封装HttpEntity对象
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, headers);
        // 4.发出请求
        return restTemplate.postForEntity(targetUrl, requestEntity, String.class);
    }

    @Override
    public ResponseEntity<String> getWithRequestHeader(String targetUrl, HashMap<String, String> headerMap) {
        logger.info("getWithRequestHeader工具类被调用");
        // 1.设置请求头参数
        HttpHeaders headers = new HttpHeaders();
        for (String headerKey : headerMap.keySet()) {
            headers.add(headerKey, headerMap.get(headerKey));
        }
        // 2.请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        // 3.发出请求
        return restTemplate.exchange(targetUrl,HttpMethod.GET,formEntity,String.class);
    }


}
