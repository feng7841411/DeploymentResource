package com.feng.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.entity.LogInfo.LogInfo;
import com.feng.entity.packageStatusEntity.BeRemovedPackage;
import com.feng.entity.returnClass.Result;
import com.feng.service.impl.BeRemovedPackageServiceImpl;
import com.feng.service.impl.LogInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng
 * @date 2022/9/22
 * @time 22:55
 * @apiNote
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/removedPackage")
public class BeRemovedPackageController {


    private final BeRemovedPackageServiceImpl beRemovedPackageService;


    private final LogInfoServiceImpl logInfoService;

    private static final Logger logger = LogManager.getLogger(BeRemovedPackageController.class);

    public BeRemovedPackageController(BeRemovedPackageServiceImpl beRemovedPackageService, LogInfoServiceImpl logInfoService) {
        this.beRemovedPackageService = beRemovedPackageService;
        this.logInfoService = logInfoService;
    }

    @GetMapping("/page")
    public IPage<BeRemovedPackage> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam(defaultValue = "") String packageName,
                                            @RequestParam(defaultValue = "") String packageAuthor) {
        IPage<BeRemovedPackage> page = new Page<>(pageNum,pageSize);
        QueryWrapper<BeRemovedPackage> beRemovedPackageQueryWrapper = new QueryWrapper<>();
        beRemovedPackageQueryWrapper .like("BE_REMOVED_PACKAGE_NAME",packageName);
        beRemovedPackageQueryWrapper .like("BE_REMOVED_PACKAGE_AUTHOR",packageAuthor);
        beRemovedPackageQueryWrapper .select().orderByDesc("BE_REMOVED_PACKAGE_TIME");
        return beRemovedPackageService.page(page,beRemovedPackageQueryWrapper);
    }



    @PostMapping("/deletePackageById")
    public Result deletePackageById(@RequestBody Map<String,Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        System.out.println(params);
        logger.info("?????????????????????");
        Integer packageId = (Integer) map.get("packageId");
        Integer integer = beRemovedPackageService.deleteRemovedPackageById(packageId);
        if (integer != 0) {
            logger.info("??????:" + packageId);
            logInfoService.insertDeleteLogInfo(params);
            return Result.success("????????????????????????","");
        }else {
            return Result.error("400","?????????????????????????????????");
        }
    }



    @PostMapping("/publishPackageAgainstById")
    public Result publishPackageAgainstById(@RequestBody Map<String,Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        System.out.println(params);
        logger.info("???????????????????????????");
        Integer packageId = (Integer) map.get("packageId");
        Integer integer = beRemovedPackageService.publishPackageAgainst(packageId);
        if (integer != 0) {
            logger.info("??????:" + packageId);
            //logInfoService.insertPublishLogInfo(params);
            return Result.success("????????????????????????","");
        }else {
            return Result.error("400","?????????????????????????????????");
        }
    }



    @GetMapping("/getRemovedPackageAsideNumber")
    public Result getRemovedPackageAsideNumber() {
        Integer removedPackageAsideNumber = beRemovedPackageService.getRemovedPackageAsideNumber();
        logger.info("????????????????????????????????????");
        return Result.success(removedPackageAsideNumber);
    }




}
