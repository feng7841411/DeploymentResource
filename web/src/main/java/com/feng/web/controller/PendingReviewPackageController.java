package com.feng.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.entity.packageStatusEntity.PendingReviewPackage;
import com.feng.entity.returnClass.Result;
import com.feng.service.impl.LogInfoServiceImpl;
import com.feng.service.impl.PendingReviewPackageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/21
 * Time: 13:56
 * Description:
 * <p>
 * 2022年10月4日 16点05分
 * 补充，这个接口是当时从
 *
 * @author feng
 */
@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/pendingReviewPackage")
public class PendingReviewPackageController {


    private final PendingReviewPackageServiceImpl pendingReviewPackageService;

    private static final Logger logger = LogManager.getLogger(PendingReviewPackageController.class);


    private final LogInfoServiceImpl logInfoService;

    public PendingReviewPackageController(PendingReviewPackageServiceImpl pendingReviewPackageService, LogInfoServiceImpl logInfoService) {
        this.pendingReviewPackageService = pendingReviewPackageService;
        this.logInfoService = logInfoService;
    }

    @GetMapping("/page")
    public IPage<PendingReviewPackage> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "") String packageName, @RequestParam(defaultValue = "") String packageAuthor) {
        IPage<PendingReviewPackage> page = new Page<>(pageNum, pageSize);
        QueryWrapper<PendingReviewPackage> pendingReviewPackageQueryWrapper = new QueryWrapper<>();
        pendingReviewPackageQueryWrapper.like("PENDING_REVIEW_PACKAGE_NAME", packageName);
        pendingReviewPackageQueryWrapper.like("PENDING_REVIEW_PACKAGE_AUTHOR", packageAuthor);
        pendingReviewPackageQueryWrapper.select().orderByDesc("PENDING_REVIEW_PACKAGE_TIME");
        logger.info("待审核资源包列表查询");
        return pendingReviewPackageService.page(page, pendingReviewPackageQueryWrapper);
    }

    @PostMapping("/test/generateSomePendingReviewPackage")
    public Result generateSomePendingReviewPackage() {
        Integer integer = pendingReviewPackageService.generateSomePendingReviewPackage();
        return Result.success("注入" + integer + "条模拟待审核记录", "");
    }

    @PostMapping("/refuseById")
    public Result refusePackageById(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        Integer packageId = (Integer) map.get("packageId");
        Integer integer = pendingReviewPackageService.refusePendingReviewPackageById(packageId);
        // 这边理论上应该删除包，但是如果这里拒绝记录，那个包实际上也被索引不到了
        if (integer != 0) {
            logger.info("拒绝" + packageId);
            logInfoService.insertRefuseLogInfo(params);
            return Result.success("已拒绝", "");
        } else {
            return Result.error("400", "修改失败，可能包不存在");
        }
    }

    @PostMapping("/checkedById")
    public Result checkedById(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        Integer packageId = (Integer) map.get("packageId");
        Integer integer = pendingReviewPackageService.checkPendingReviewPackageById(packageId);
        if (integer != 0) {
            logger.info("安全扫描" + packageId);
            logInfoService.insertCheckLogInfo(params);
            return Result.success("完成安全扫描", "");
        } else {
            return Result.error("400", "修改失败，可能包不存在");
        }
    }

    @PostMapping("/publishById")
    public Result publishById(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        Integer packageId = (Integer) map.get("packageId");
        Integer integer = pendingReviewPackageService.publishPendingReviewPackageById(packageId);
        if (integer != 0) {
            logger.info("安全扫描" + packageId);
            logInfoService.insertPublishLogInfo(params);
            return Result.success("已发布", "");
        } else {
            return Result.error("400", "发布失败，可能包不存在");
        }
    }


    /**
     * 请求获取待审核的记录条数，显示在Aside，这个不需要传参数
     *
     * @return
     */
    @GetMapping("/getPendingPackageAsideNumber")
    public Result getPendingPackageAsideNumber() {
        Integer pendingReviewAsideNumber = pendingReviewPackageService.getPendingReviewAsideNumber();
        logger.info("待审核记录数目查询被调用");
        return Result.success(pendingReviewAsideNumber);
    }


}
