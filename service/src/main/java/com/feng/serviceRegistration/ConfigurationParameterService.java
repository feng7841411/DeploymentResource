package com.feng.serviceRegistration;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.returnClass.ServiceResult;


/**
 * @author: 冯金河
 * @Date: 2022/11/15 09:03
 * @Description:
 */


public interface ConfigurationParameterService extends IService<ConfigurationParameter> {

    public ServiceResult insertStringParameter(String name, String value);

    public ServiceResult getStringParameter(String name);

    public ServiceResult deleteStringParameter(String name);

    public ServiceResult getAllStringParameter();


    public ServiceResult insertIntParameter(String name, Integer value);

    public ServiceResult getIntParameter(String name);

    public ServiceResult deleteIntParameter(String name);

    public ServiceResult getAllIntParameter();


    public boolean isExistParameter(String name);

}
