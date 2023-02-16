package com.feng.serviceRegistration.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.feng.dao.ConfigurationParameterMapper;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.serviceRegistration.ConfigurationParameter;
import com.feng.serviceRegistration.ConfigurationParameterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 冯金河
 * @Date: 2022/11/15 09:04
 * @Description:
 */
@Service
public class ConfigurationParameterServiceImpl extends ServiceImpl<ConfigurationParameterMapper, ConfigurationParameter> implements ConfigurationParameterService {

    private final ConfigurationParameterMapper configurationParameterMapper;

    public ConfigurationParameterServiceImpl(ConfigurationParameterMapper configurationParameterMapper) {
        this.configurationParameterMapper = configurationParameterMapper;
    }

    @Override
    public ServiceResult insertStringParameter(String name, String value) {
        final ConfigurationParameter configurationParameter = new ConfigurationParameter();
        configurationParameter.setParameterName(name);
        configurationParameter.setStringValue(value);
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME",name);
        if (isExistParameter(name)) {
            // 更新
            final int update = configurationParameterMapper.update(configurationParameter, configurationParameterQueryWrapper);
            if (update == 1){
                return ServiceResult.success();
            } else {
                return ServiceResult.error();
            }
        } else {
            // 插入
            final int insert = configurationParameterMapper.insert(configurationParameter);
            if (insert == 1) {
                return ServiceResult.success();
            } else {
                return ServiceResult.error();
            }
        }

    }

    @Override
    public ServiceResult getStringParameter(String name) {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME",name);
        final ConfigurationParameter configurationParameter = configurationParameterMapper.selectOne(configurationParameterQueryWrapper);
        if (configurationParameter!=null) {
            return ServiceResult.success("200",configurationParameter.getStringValue());
        } else {
            return ServiceResult.error();
        }

    }

    @Override
    public ServiceResult deleteStringParameter(String name) {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME",name);
        final int delete = configurationParameterMapper.delete(configurationParameterQueryWrapper);
        if (delete==1) {
            return ServiceResult.success();
        }else {
            return ServiceResult.error();
        }

    }

    @Override
    public ServiceResult getAllStringParameter() {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.isNotNull("STRING_VALUE");
        final List<ConfigurationParameter> configurationParameters = configurationParameterMapper.selectList(configurationParameterQueryWrapper);
        return ServiceResult.success("200", configurationParameters);
    }

    @Override
    public ServiceResult insertIntParameter(String name, Integer value) {
        final ConfigurationParameter configurationParameter = new ConfigurationParameter();
        configurationParameter.setParameterName(name);
        configurationParameter.setIntValue(value);
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME",name);
        if (isExistParameter(name)) {
            // 更新
            final int update = configurationParameterMapper.update(configurationParameter, configurationParameterQueryWrapper);
            if (update == 1){
                return ServiceResult.success();
            } else {
                return ServiceResult.error();
            }
        } else {
            // 插入
            final int insert = configurationParameterMapper.insert(configurationParameter);
            if (insert == 1) {
                return ServiceResult.success();
            } else {
                return ServiceResult.error();
            }
        }
    }

    @Override
    public ServiceResult getIntParameter(String name) {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME",name);
        final ConfigurationParameter configurationParameter = configurationParameterMapper.selectOne(configurationParameterQueryWrapper);
        if (configurationParameter!=null) {
            return ServiceResult.success("200",configurationParameter.getIntValue());
        } else {
            return ServiceResult.error();
        }

    }

    @Override
    public ServiceResult deleteIntParameter(String name) {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME",name);
        final int delete = configurationParameterMapper.delete(configurationParameterQueryWrapper);
        if (delete==1) {
            return ServiceResult.success();
        }else {
            return ServiceResult.error();
        }

    }

    @Override
    public ServiceResult getAllIntParameter() {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.isNotNull("INT_VALUE");
        final List<ConfigurationParameter> configurationParameters = configurationParameterMapper.selectList(configurationParameterQueryWrapper);
        return ServiceResult.success("200", configurationParameters);
    }

    @Override
    public boolean isExistParameter(String name) {
        final QueryWrapper<ConfigurationParameter> configurationParameterQueryWrapper = new QueryWrapper<>();
        configurationParameterQueryWrapper.eq("PARAMETER_NAME", name);
        final List<ConfigurationParameter> configurationParameters =
                configurationParameterMapper.selectList(configurationParameterQueryWrapper);
        if (configurationParameters == null) {
            return false;
        } else if (configurationParameters.size() == 0) {
            return false;
        } else {
            return true;
        }

    }
}
