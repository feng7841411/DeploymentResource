package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.LogInfo.LogInfo;
import com.feng.entity.returnClass.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/10/7
 * Time: 14:38
 * Description:
 *
 * 软件商店日志中心的逻辑类，主要包括日志的增删改查
 * 其实主要就是增加记录，每个模块操作以后，调用一下这里的插入方法，写入一条日志记录
 *
 * 查询，就是给前端一个分页查询，作为数据库的日志，最多就是留一个删除的入口
 * 基本不会有Update的操作
 *
 * 分页查询直接在Controller查就行了
 * @author feng
 */

public interface LogInfoService extends IService<LogInfo> {

    /**
     * 日志记录插入操作
     * @param logInfo
     * @return-
     */
    public ServiceResult insertLogInfo(LogInfo logInfo);


    /**
     * 【资源包删除】记录
     * @param ids
     * @return
     */
    public ServiceResult deleteLogInfoByIds(List<Integer> ids);

    /**
     * 【资源包拒绝】记录
     * @param params
     * @return
     */
    public ServiceResult insertRefuseLogInfo(Map<String, Object> params);


    /**
     * 【资源包发布】记录
     * @param params
     * @return
     */
    public ServiceResult insertPublishLogInfo(Map<String, Object> params);


    /**
     * 【资源包下架】记录
     * @param params
     * @return
     */
    public ServiceResult insertRemoveLogInfo(Map<String, Object> params);


    /**
     * 【资源包安全检查】记录
     * @param params
     * @return
     */
    public ServiceResult insertCheckLogInfo(Map<String, Object> params);


    /**
     * 【资源包删除】 记录
     * @param params
     * @return
     */
    public ServiceResult insertDeleteLogInfo(Map<String, Object> params);

}
