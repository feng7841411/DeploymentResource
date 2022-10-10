package com.feng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.feng.entity.LogInfo.LogInfo;
import com.feng.entity.packageStatusEntity.PendingReviewPackage;
import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.PackageService;
import com.feng.utils.JsonUtils;
import com.feng.utils.ZipUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


    final ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService;


    final PendingReviewPackageServiceImpl pendingReviewPackageService;

    final LogInfoServiceImpl logInfoService;

    public PackageServiceImpl(ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService, PendingReviewPackageServiceImpl pendingReviewPackageService, LogInfoServiceImpl logInfoService) {
        this.servicePackageDetailInfoService = servicePackageDetailInfoService;
        this.pendingReviewPackageService = pendingReviewPackageService;
        this.logInfoService = logInfoService;
    }


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
            return ServiceResult.success("文件：" + originalFilename + "存储成功", fileUid);
        } catch (IOException e) {

            e.printStackTrace();
            return ServiceResult.error("zip文件往存储位置写入失败", null);
        }

    }

    /**
     * 文件的起始地址和目标输出地址都是确定的，只需要把想解压的zip文件的Uid传过来即可
     *
     * @param fileUid
     * @return
     */
    @Override
    public ServiceResult unZipPackage(String fileUid) {
        File srcFile = new File(fileUploadPath + '/' + fileUid);
        File file = new File(fileUnZipPath);
        if (!file.exists()) {
            file.mkdir();
            logger.info("临时解压文件夹不存在，已经新建");
        }
        try {
            ZipUtils.unZip(srcFile, fileUnZipPath);
            return ServiceResult.success();
        } catch (Exception e) {
            return ServiceResult.error("解压失败", e.toString());
        }
    }

    @Override
    public ServiceResult readJsonFileFromUnZip() {
        File unZip = new File(fileUnZipPath);
        File[] files = unZip.listFiles();
        File jsonFile = null;
        for (File file : files) {
            String fileType = FileUtil.extName(file);
            logger.info("文件类型：" + fileType);
            if (fileType.equals("json")) {
                jsonFile = file;
            }
        }
        if (jsonFile == null) {
            return ServiceResult.error("解析失败，不存在json描述文件", null);
        } else {
            PackingToolInfo packingToolInfo = JsonUtils.jsonFile2Object(jsonFile.toString(), PackingToolInfo.class);
            logger.info("json描述文件解析结果： ");
            logger.info(packingToolInfo.toString());
            if (packingToolInfo != null) {
                return ServiceResult.success("读取json文件成功", packingToolInfo);
            } else {
                return ServiceResult.error("解析失败，资源包描述文件解读失败", null);
            }
        }

    }

    @Override
    public ServiceResult getInfoFromPackingToolInfo(PackingToolInfo packingToolInfo) {
        return null;
    }

    @Override
    public ServiceResult clearUnZipFiles() {
        logger.info("存放临时解压文件的目录是： " + fileUnZipPath);
        logger.info("准备清空临时解压文件夹");
        File file = new File(fileUnZipPath);
        if (!file.isDirectory()) {
            String error = "无法删除，因为目标路径不是一个文件夹";
            logger.error(error);
            return ServiceResult.error(error, "");
        } else {
            Integer fileNumber = file.listFiles().length;
            logger.info("目前文件夹下有【" + fileNumber + "】个文件夹或者文件");
            if (FileUtil.isEmpty(file)) {
                logger.info("目标文件夹已经是空，不需要进行清理");
                return ServiceResult.success();
            } else {
                boolean clean = FileUtil.clean(file);
                if (clean) {
                    logger.info("临时解压文件夹清理完成");
                    return ServiceResult.success("临时解压文件夹清理完成", "");
                } else {
                    logger.info("清理临时解压文件夹失败，可能存在IO错误，导致文件删除不完全，已终止");
                    return ServiceResult.error("清理临时解压文件夹失败，可能存在IO错误，导致文件删除不完全，已终止", "");
                }
            }
        }
    }

    @Override
    public ServiceResult confirmUploadPackage(Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String uploader = (String) map.get("uploader");
        Integer servicePackageDetailInfoId = (Integer) map.get("servicePackageDetailInfoId");
        logger.info("资源包上传确认消息，上传者：" + uploader + " 对应详情记录的主键：" + servicePackageDetailInfoId);
        PendingReviewPackage pendingReviewPackage = new PendingReviewPackage();
        logger.info("调出资源包详细信息：");
        ServiceResult serviceResult = servicePackageDetailInfoService.selectServicePackageDetailInfoById(servicePackageDetailInfoId);
        ServicePackageDetailInfo servicePackageDetailInfo = (ServicePackageDetailInfo) serviceResult.getData();
        pendingReviewPackage.setPendingReviewPackageName(servicePackageDetailInfo.getConnectedPackageOriginalFileName());
        pendingReviewPackage.setPendingReviewPackageAuthor(uploader);
        pendingReviewPackage.setPendingReviewPackageType("image");
        pendingReviewPackage.setPendingReviewPackageTime(new Date());
        pendingReviewPackage.setPendingReviewPackageStatus("待审核");
        pendingReviewPackage.setConnectedPackageUid(servicePackageDetailInfo.getConnectedPackageUid());
        pendingReviewPackage.setConnectedDetailInfoId(servicePackageDetailInfoId);
        Integer integer = pendingReviewPackageService.insertPendingReviewPackage(pendingReviewPackage);
        if (integer == 1) {
            logger.info("资源包确认完成");
            // 调用日志组件，写入数据库
            LogInfo logInfo = new LogInfo();
            // Operator 在 LogInfoTime 做了 Action, 目标是Object
            logInfo.setOperator(uploader);
            logInfo.setAction("上传新资源");
            logInfo.setObject(servicePackageDetailInfo.getConnectedPackageOriginalFileName());
            logInfo.setLogInfoTime(new Date());
            logInfoService.insertLogInfo(logInfo);
            return ServiceResult.success();
        } else {
            logger.info("资源包确认出错，pendingReview记录写入失败");
            return ServiceResult.error();
        }
    }

    @Override
    public ServiceResult cancelUploadPackage(Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String servicePackageDetailInfoId = (String) map.get("servicePackageDetailInfoId");
        servicePackageDetailInfoService.cancelServicePackageDetailInfoById(Integer.valueOf(servicePackageDetailInfoId));
        return ServiceResult.success();
    }

    @Override
    public ServiceResult getMyPackage(Map<String, Object> params) {
        return null;
    }
}
