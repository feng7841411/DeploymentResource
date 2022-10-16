package com.feng.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.impl.BePublishedPackageServiceImpl;
import com.feng.service.impl.LogInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/22
 * Time: 15:07
 * Description:
 *
 * @author feng
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/publishedPackage")
public class BePublishedPackageController {



    private final BePublishedPackageServiceImpl bePublishedPackageService;

    private final LogInfoServiceImpl logInfoService;

    private static final Logger logger = LogManager.getLogger(BePublishedPackageController.class);

    public BePublishedPackageController(BePublishedPackageServiceImpl bePublishedPackageService, LogInfoServiceImpl logInfoService) {
        this.bePublishedPackageService = bePublishedPackageService;
        this.logInfoService = logInfoService;
    }


    @GetMapping("/page")
    public IPage<BePublishedPackage> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                @RequestParam(defaultValue = "") String packageName,
                                                @RequestParam(defaultValue = "") String packageAuthor) {
        IPage<BePublishedPackage> page = new Page<>(pageNum,pageSize);
        QueryWrapper<BePublishedPackage> bePublishedPackageQueryWrapper = new QueryWrapper<>();
        bePublishedPackageQueryWrapper .like("BE_PUBLISHED_PACKAGE_NAME",packageName);
        bePublishedPackageQueryWrapper .like("BE_PUBLISHED_PACKAGE_AUTHOR",packageAuthor);
        bePublishedPackageQueryWrapper .select().orderByDesc("BE_PUBLISHED_PACKAGE_TIME");
        logger.info("已发布资源包列表查询");
        return bePublishedPackageService.page(page,bePublishedPackageQueryWrapper);
    }



    @PostMapping("/removePackageById")
    public Result removePackageById(@RequestBody Map<String,Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        System.out.println(params);
        logger.info("下架方法被调用");
        Integer packageId = (Integer) map.get("packageId");
        Integer integer = bePublishedPackageService.removePackageById(packageId);
        if (integer != 0) {
            logger.info("下架:" + packageId);
            logInfoService.insertRemoveLogInfo(params);
            return Result.success("下架操作成功","");
        }else {
            return Result.error("400","修改失败，可能包不存在");
        }
    }

    @GetMapping("/getPublishedPackageAsideNumber")
    public Result getPublishedPackageAsideNumber() {
        Integer publishedPackageAsideNumber = bePublishedPackageService.getPublishedPackageAsideNumber();
        logger.info("已发布记录数目查询被调用");
        return Result.success(publishedPackageAsideNumber);
    }


    @GetMapping("/getAllPublishedPackage")
    public Result getAllPublishedPackageInfo() {
        // 给开设部署用查询，把已发布的包信息全部给出去
        ServiceResult allBePublishedPackageInfo = bePublishedPackageService.getAllBePublishedPackageInfo();
        return Result.success("已发布资源信息全查询",allBePublishedPackageInfo.getData());
    }

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @GetMapping("/getZipFile")
    public void download(@RequestParam("zipFileKey") String zipFileName,
                         HttpServletResponse response) throws Exception {
        File file = new File(fileUploadPath +"/" + zipFileName).getAbsoluteFile();
        System.out.println(file.toString());
        if (!file.exists()) {
        } else {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        }


    }




}
