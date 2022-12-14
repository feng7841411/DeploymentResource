package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.LogInfoDao.LogInfoMapper;
import com.feng.entity.LogInfo.LogInfo;
import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.LogInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/10/7
 * Time: 14:39
 * Description:
 *
 * @author feng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {


    private final LogInfoMapper logInfoMapper;

    private static final Logger logger = LogManager.getLogger(LogInfoServiceImpl.class);

    final
    ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService;

    public LogInfoServiceImpl(LogInfoMapper logInfoMapper, ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService) {
        this.logInfoMapper = logInfoMapper;
        this.servicePackageDetailInfoService = servicePackageDetailInfoService;
    }

    @Override
    public ServiceResult insertLogInfo(LogInfo logInfo) {
        logger.info("接收到一条LogInfo写入请求");
        logger.info("logInfo内容：" + logInfo.toString());
        int insert = logInfoMapper.insert(logInfo);
        if (insert == 1) {
            logger.info("logInfo写入成功");
            return ServiceResult.success();
        } else {
            logger.error("logInfo写入失败");
            // error的默认码是500
            return ServiceResult.error();
        }

    }

    @Override
    public ServiceResult deleteLogInfoByIds(List<Integer> ids) {
        int i = logInfoMapper.deleteBatchIds(ids);
        logger.info(i + "条logInfo记录被删除");
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertUploaderConfirmLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("上传资源包");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertUploaderCancelLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("上传资源包取消");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertRefuseLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("拒绝资源包上架");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertPublishLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("资源包上架");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertRemoveLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("资源包下架");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertCheckLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("资源包扫描完成");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult insertDeleteLogInfo(Map<String, Object> params) {
        ServiceResult serviceResult = makeLogInfoBasic(params);
        LogInfo logInfo = (LogInfo) serviceResult.getData();
        logInfo.setAction("资源包删除");
        logInfoMapper.insert(logInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult makeLogInfoBasic(Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String operator = (String) map.get("operator");
        Integer servicePackageDetailInfoId = (Integer) map.get("servicePackageDetailInfoId");
        ServicePackageDetailInfo servicePackageDetailInfo = null;
        if (servicePackageDetailInfoId != null) {
            ServiceResult serviceResult = servicePackageDetailInfoService.selectServicePackageDetailInfoById(servicePackageDetailInfoId);
            servicePackageDetailInfo = (ServicePackageDetailInfo) serviceResult.getData();
        }
        LogInfo logInfo = new LogInfo();
        if (operator!=null) {
            logInfo.setOperator(operator);
        }
        // action不填，让其他方法来填
        logInfo.setObject(servicePackageDetailInfo.getConnectedPackageOriginalFileName());
        logInfo.setLogInfoTime(new Date());
        return ServiceResult.success("200",logInfo);
    }


}
