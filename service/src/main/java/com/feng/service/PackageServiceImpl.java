package com.feng.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.utils.JsonUtils;
import com.feng.utils.ZipUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author feng
 * @date 2022/10/4
 * @time 17:51
 * @apiNote
 */

@Service
public class PackageServiceImpl implements PackageService {

    @Value("${files.upload.path}")
    private String fileUploadPath;


    @Value("${files.unZip.path}")
    private String fileUnZipPath;

    private static final Logger logger = LogManager.getLogger(PackageServiceImpl.class);


    @Override
    public ServiceResult storePackage(MultipartFile file) {
        // file = "xxfw-xttz-xttz-java.zip"
        String originalFilename = file.getOriginalFilename();
        String contentType = FileUtil.extName(originalFilename);
        // 定义文件唯一标识码
        // fileUid = "xxxxxx.zip"
        String fileUid = IdUtil.fastSimpleUUID() + StrUtil.DOT + contentType;
        File uploadFile = new File(fileUploadPath + '/' + fileUid);
        // 需要转绝对路径
        File dest = uploadFile.getAbsoluteFile();
        // 判断文件存储路径是否存在，若无则新建路径
        File parentFile = uploadFile.getParentFile();
        logger.info("originalFileName: " + originalFilename);
        logger.info("contentType: " + contentType);
        logger.info("fileUid: " + fileUid);
        logger.info("uploadFile: " + uploadFile);
        logger.info("dest: " + dest);
        logger.info("parentFile: " + parentFile);
        if (!parentFile.exists()) {
            parentFile.mkdir();
            logger.info("当前文件存储路径：" + parentFile + "不存在，已经新建");
        }
        try {
            file.transferTo(dest);
            return ServiceResult.success("文件：" + originalFilename + "存储成功",fileUid);
        } catch (IOException e) {

            e.printStackTrace();
            return ServiceResult.error("zip文件往存储位置写入失败",null);
        }

    }

    /**
     * 文件的起始地址和目标输出地址都是确定的，只需要把想解压的zip文件的Uid传过来即可
     * @param fileUid
     * @return
     */
    @Override
    public ServiceResult unZipPackage(String fileUid) {
        File srcFile = new File(fileUploadPath + '/' + fileUid);
        try {
            ZipUtils.unZip(srcFile,fileUnZipPath);
            return ServiceResult.success();
        }catch (Exception e){
            return ServiceResult.error("解压失败",e.toString());
        }
    }

    @Override
    public ServiceResult readJsonFileFromUnZip() {
        File unZip = new File(fileUnZipPath);
        File[] files = unZip.listFiles();
        File jsonFile = null;
        for (File file: files) {
            String fileType = FileUtil.extName(file);
            logger.info("文件类型：" + fileType);
            if (fileType.equals("json")){
                jsonFile = file;
            }
        }
        if (jsonFile == null) {
            return ServiceResult.error("解析失败，不存在json描述文件",null);
        } else {
            PackingToolInfo packingToolInfo = JsonUtils.jsonFile2Object(jsonFile.toString(), PackingToolInfo.class);
            logger.info("json描述文件解析结果： ");
            logger.info(packingToolInfo.toString());
            if (packingToolInfo!=null) {
                return ServiceResult.success("读取json文件成功", packingToolInfo);
            }else {
                return ServiceResult.error("解析失败，资源包描述文件解读失败",null);
            }
        }

    }

    @Override
    public ServiceResult getInfoFromPackingToolInfo(PackingToolInfo packingToolInfo) {
        return null;
    }
}
