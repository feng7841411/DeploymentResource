package com.feng.service;

import com.feng.entity.returnClass.ServiceResult;

import java.util.Map;

/**
 * @author feng
 * @date 2022/10/10
 * @time 16:25
 * @apiNote
 */



public interface MyResourcesService {


    /**
     * 查询这个账号的所有资源包
     * @param params
     * @return
     */
    public ServiceResult getMyResources(Map<String, Object> params);


}
