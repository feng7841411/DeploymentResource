package com.feng.web.controller;

import com.feng.entity.desertedEntity.FileInfo;
import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.impl.FileInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/14
 * Time: 14:30
 * Description:
 *
 * @author feng
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/file")
public class FileController {


    private final FileInfoServiceImpl fileInfoService;

    private static final Logger logger = LogManager.getLogger(FileController.class);

    public FileController(FileInfoServiceImpl fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    /**
     *
     */




    @PostMapping("/uploadPackageTest")
    public Result uploadPackageTest(@RequestParam MultipartFile file) {
        // 读取File 基本信息
        FileInfo fileInfo = fileInfoService.storeAndGetFileInfo(file);
        if (fileInfo == null){
            return Result.error("500","上传文件失败");
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success("接收新资源文件成功","");
    }

}
