package com.feng.web.serviceRegistrationController;


import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.serviceRegistration.ServiceAddress;
import com.feng.serviceRegistration.impl.ConfigurationParameterServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 冯金河
 * @Date: 2022/11/16 09:27
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/configurationParameter")
public class ConfigurationParameterController {

    private static final Logger logger = LogManager.getLogger(ConfigurationParameterController.class);


    private final ConfigurationParameterServiceImpl configurationParameterService;


    public ConfigurationParameterController(ConfigurationParameterServiceImpl configurationParameterService) {
        this.configurationParameterService = configurationParameterService;
    }

    @PostMapping("/insertStringConfigurationParameter")
    public Result insertNewStringConfigurationParameter(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String parameterName = (String) map.get("parameterName");
        String parameterValue = (String) map.get("parameterValue");
        final ServiceResult serviceResult = configurationParameterService.insertStringParameter(parameterName, parameterValue);

        if (serviceResult.isSuccess()) {
            String successStr = "写入或更新： " + parameterName + "=" + parameterValue;
            return Result.success(successStr, "");
        } else {
            String errorStr = "写入或更新，失败";
            return Result.error("403", errorStr);
        }
    }
    @GetMapping("/getAllStringParameters")
    public Result getAllStringParameters() {
        final ServiceResult allStringParameter = configurationParameterService.getAllStringParameter();
        List<ServiceAddress> list = (List<ServiceAddress>) allStringParameter.getData();
        return Result.success("", list);
    }



    @PostMapping("/deleteStringConfigurationParameter")
    public Result deleteStringConfigurationParameter(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String parameterName = (String) map.get("parameterName");
        final ServiceResult serviceResult = configurationParameterService.deleteIntParameter(parameterName);
        if (serviceResult.isSuccess()) {
            String successStr = "移除配置参数： " + parameterName;
            return Result.success(successStr, "");
        } else {
            String errorStr = "移除配置参数失败，可能出现数据库操作错误，或者试图移除不存在的参数";
            return Result.error("403", errorStr);
        }
    }

    @GetMapping("/getStringConfigurationParameterByKey")
    public Result getStringConfigurationParameterByKey(@RequestParam String parameterKey) {

        logger.info("接收到配置参数查询请求，key=" + parameterKey);
        final ServiceResult stringParameter = configurationParameterService.getStringParameter(parameterKey);
        if (stringParameter.isSuccess()) {
            return Result.success("",stringParameter.getData());
        }else {
            return Result.error();
        }

    }

}
