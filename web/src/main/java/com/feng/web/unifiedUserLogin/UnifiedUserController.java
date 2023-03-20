package com.feng.web.unifiedUserLogin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.feng.entity.User.User;
import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.serviceRegistration.impl.ConfigurationParameterServiceImpl;
import com.feng.utils.impl.RestTemplateServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author: 冯金河
 * @Date: 2023/2/13 17:32
 * @Description: 1、应用注册接口
 * <p>
 * 2、单点登录用户名密码校验接口
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/unifiedUser")
public class UnifiedUserController {

    private final ConfigurationParameterServiceImpl configurationParameterService;

    private final RestTemplateServiceImpl restTemplateService;

    private static final Logger logger = LogManager.getLogger(UnifiedUserController.class.getName());

    public UnifiedUserController(ConfigurationParameterServiceImpl configurationParameterService, RestTemplateServiceImpl restTemplateService) {
        this.configurationParameterService = configurationParameterService;
        this.restTemplateService = restTemplateService;
    }

    @PostMapping("/login")
    public Result unifiedUserLogin(@RequestBody User user) {
        String name = user.getUsername();
        String password = user.getPassword();
        logger.info("统一用户单点登录接口被调用，传入用户名：" + name + ",传入密码：" + password);
        logger.info("尝试获取登录认证接口");
        final ServiceResult authTokenLoginApi = configurationParameterService.getStringParameter("authTokenLoginApi");
        if (!authTokenLoginApi.isSuccess()) {
            return Result.error("500", "系统未配置统一用户服务-用户名密码认证接口,请检查");
        } else {
            final ServiceResult clientName = configurationParameterService.getStringParameter("clientName");
            final ServiceResult clientId = configurationParameterService.getStringParameter("clientId");
            final ServiceResult clientSecret = configurationParameterService.getStringParameter("clientSecret");
            final ServiceResult redirectUri = configurationParameterService.getStringParameter("redirectUri");
            if (!(clientName.isSuccess() && clientId.isSuccess() && clientSecret.isSuccess() && redirectUri.isSuccess())) {
                return Result.error("500", "系统未在统一用户服务进行应用注册，请检查");
            } else {
                final String authTokenLoginApiUrl = (String) authTokenLoginApi.getData();
                final MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
                multiValueMap.add("userName", name);
                multiValueMap.add("password", password);
                multiValueMap.add("clientId", (String) clientId.getData());
                multiValueMap.add("clientSecret", (String) clientSecret.getData());
                multiValueMap.add("type","");
                final ResponseEntity<String> stringResponseEntity = restTemplateService.postParamsRequest(authTokenLoginApiUrl, multiValueMap);
                if (stringResponseEntity.getStatusCode().is2xxSuccessful()) {
                    final String body = stringResponseEntity.getBody();
                    final JSONObject responseJsonObject = JSON.parseObject(body);
                    final int code = (int) responseJsonObject.get("code");
                    final int GOOD_CODE = 200;
                    if (code == GOOD_CODE) {
                        logger.info("调用认证接口成功");
                        JSONObject data = (JSONObject) responseJsonObject.get("data");
                        logger.info("获取到的data:" + data.toString());
                        // 准备一个JSONObject给前端，让前端往localhost里写令牌
                        JSONObject tokenObject = new JSONObject();
                        tokenObject.put("accessToken", data.get("access_token"));
                        tokenObject.put("refreshToken", data.get("access_token"));
                        tokenObject.put("clientId", (String) clientId.getData());
                        tokenObject.put("clientSecret", (String) clientSecret.getData());
                        tokenObject.put("type", "login");
                        logger.info("准备返回");
                        return Result.success("统一用户服务-用户名密码校验通过", tokenObject);
                    } else {
                        return Result.error("500", "统一用户服务-用户名密码校验不通过");
                    }
                } else {
                    return Result.error("500", "统一用户服务-用户名密码校验接口调用失败");
                }

            }

        }
    }


    @PostMapping("/applicationRegister")
    public Result applicationRegister(@RequestBody HashMap<String, String> map) {
        logger.info("应用注册接口applicationRegister被调用");
        logger.info("传入参数：" + map.toString());
        String[] parameters = {"clientName", "clientId", "clientSecret", "clientType", "redirectUri"};
        for (String parameter : parameters) {
            logger.info(parameter + "=" + map.get(parameter));
        }
        logger.info("尝试查询统一用户服务-用户注册接口");
        final ServiceResult registerByClientId = configurationParameterService.getStringParameter("registerByClientId");
        if (!registerByClientId.isSuccess()) {
            return Result.error("500", "系统没有配置统一用户-应用注册接口，请检查");
        } else {
            String applicationRegisterUrl = (String) registerByClientId.getData();
            final ResponseEntity<String> stringResponseEntity = restTemplateService.postJsonString(applicationRegisterUrl, JSON.toJSONString(map));
            if (stringResponseEntity.getStatusCode().is2xxSuccessful()) {
                final String body = stringResponseEntity.getBody();
                final JSONObject jsonObject = JSON.parseObject(body);
                final int GOOD_CODE = 200;
                int code = (int) jsonObject.get("code");
                if (code == GOOD_CODE) {
                    for (String parameter : parameters) {
                        configurationParameterService.insertStringParameter(parameter, (map.get(parameter)));
                    }
                    return Result.success("应用在统一用户服务端注册成功！", "");
                } else {
                    return Result.error("500", "调用统一用户-应用注册接口成功，但是返回了异常code标识，请检查");
                }
            } else {
                return Result.error("500", "调用统一用户-应用注册接口失败，请检查");
            }
        }
    }

    @PostMapping("/getUserCnNameByAccessToken")
    public Result getUserCnNameByAccessToken(@RequestBody HashMap<String, String> map) {
        logger.info("getUserCnNameByAccessToken被调用");
        logger.info("传入参数；" + map.toString());
        String accessToken = map.get("accessToken");
        logger.info("分离accessToken:" + accessToken);
        final ServiceResult authUserDescribeUrl = configurationParameterService.getStringParameter("authUserDescribe");
        if (!authUserDescribeUrl.isSuccess()) {
            return Result.error("500", "未配置token解析用户信息接口");
        } else {
            HashMap<String, String> header = new HashMap<>();
            String headerKey = "Authorization";
            String headerValue = "Bearer " + accessToken;
            logger.info("准备调用token解析接口, key=" + headerKey + ",value=" + headerValue);
            header.put(headerKey,headerValue);
            final ResponseEntity<String> withRequestHeader = restTemplateService.getWithRequestHeader((String) authUserDescribeUrl.getData(), header);
            if (withRequestHeader.getStatusCode().is2xxSuccessful()) {
                final String body = withRequestHeader.getBody();
                logger.info("token解析接口HTTP返回200，接口如下：" + body);
                final JSONObject jsonObject = JSON.parseObject(body);
                JSONObject data = (JSONObject) jsonObject.get("data");
                String realName = (String) data.get("realName");
                return Result.success("",realName);
            }else {
                logger.info("token解析接口HTTP异常");
                return Result.error("500","token解析接口HTTP异常");
            }
        }
    }


}
