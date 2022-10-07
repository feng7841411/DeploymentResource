package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.LogInfoDao.LogInfoMapper;
import com.feng.entity.LogInfo.LogInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.LogInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public LogInfoServiceImpl(LogInfoMapper logInfoMapper) {
        this.logInfoMapper = logInfoMapper;
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
}
