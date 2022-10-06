package com.feng.web.controller;

import com.feng.entity.desertedEntity.FileInfo;
import com.feng.entity.returnClass.Result;
import com.feng.service.impl.FileInfoServiceImpl;
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



    public FileController(FileInfoServiceImpl fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    /**
     *
     */

    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam MultipartFile file) {
        // 读取File 基本信息
        FileInfo fileInfo = fileInfoService.storeAndGetFileInfo(file);
        if (fileInfo == null){
            return Result.error("500","上传文件失败");
        }

        // 解压读取里面的JSON，写入数据库


        // 存入JSON的PackageInfo以后，需要把ID，附在File的最后一个字段；然后FileInfo写入数据库


        // 文件编码转入静态文件夹


        // 返回结果
        return Result.success("接收镜像文件成功","");
    }
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
