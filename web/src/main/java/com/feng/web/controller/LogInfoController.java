package com.feng.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.entity.LogInfo.LogInfo;
import com.feng.service.impl.LogInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feng
 * @date 2022/10/10
 * @time 15:20
 * @apiNote
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/logInfo")
public class LogInfoController {


    private static final Logger logger = LogManager.getLogger(LogInfoController.class);


    private final LogInfoServiceImpl logInfoService;

    public LogInfoController(LogInfoServiceImpl logInfoService) {
        this.logInfoService = logInfoService;
    }


    /**
     * mybatis-plus分页查询方法
     *
     * @param pageNum  第几页
     * @param pageSize 每页多少条数据
     * @return 返回的是IPage提供给前端解析表格
     */
    @GetMapping("/page")
    public IPage<LogInfo> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                   @RequestParam(defaultValue = "") String operator,
                                   @RequestParam(defaultValue = "") String logKeyWord,
                                   @RequestParam(defaultValue = "") String timeStart,
                                   @RequestParam(defaultValue = "") String timeEnd) {
        IPage<LogInfo> page = new Page<>(pageNum, pageSize);
        //可以加筛选条件 queryWrapper.like方法 模糊匹配查询
        QueryWrapper<LogInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("OPERATOR", operator);
        queryWrapper.like("ACTION", logKeyWord);
        //数据库是新加入的数据在表的后面，这里加了一个时间排序，前端展示的时候，最新的任务排在前面
        queryWrapper.select().orderByDesc("LOG_INFO_TIME");
        logger.info("日志分页查询被调用。");
        if (!timeStart.equals("")) {
            queryWrapper.select().ge("LOG_INFO_TIME",timeStart);
        }
        if (!timeEnd.equals("")) {
            queryWrapper.select().le("LOG_INFO_TIME",timeEnd);
        }

        return logInfoService.page(page, queryWrapper);
    }


}
