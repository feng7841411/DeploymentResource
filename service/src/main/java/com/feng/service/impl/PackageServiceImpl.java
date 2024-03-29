package com.feng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
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


    final
    ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService;


    final
    PendingReviewPackageServiceImpl pendingReviewPackageService;

    public PackageServiceImpl(ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService, PendingReviewPackageServiceImpl pendingReviewPackageService) {
        this.servicePackageDetailInfoService = servicePackageDetailInfoService;
        this.pendingReviewPackageService = pendingReviewPackageService;
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
        File file = new File(fileUnZipPath);
        logger.info("解压方法unZipPackage被调用");
        logger.info("待解压文件路径：" + srcFile);
        logger.info("目标路径：" + fileUid);
        if (!file.exists()) {
            file.mkdir();
            logger.info("临时解压文件夹不存在，已经新建");
        }
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
            // 2022年10月27日 10点44分 因为多了一个挂载文件夹，挂载文件夹的type是null，然后判断type是否是json的时候报了一个错
            // 其实如果有且只有一个json,也可以直接跳出循环
            if (fileType != null) {
                if (fileType.equals("json")){
                    jsonFile = file;
                    break;
                }
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

    @Override
    public ServiceResult clearUnZipFiles() {
        logger.info("存放临时解压文件的目录是： " + fileUnZipPath);
        logger.info("准备清空临时解压文件夹");
        File file = new File(fileUnZipPath);
        if (!file.isDirectory()) {
            String error = "无法删除，因为目标路径不是一个文件夹";
            logger.error(error);
            return ServiceResult.error(error,"");
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
                    return ServiceResult.success("临时解压文件夹清理完成","");
                } else {
                    logger.info("清理临时解压文件夹失败，可能存在IO错误，导致文件删除不完全，已终止");
                    return ServiceResult.error("清理临时解压文件夹失败，可能存在IO错误，导致文件删除不完全，已终止","");
                }
            }
        }
    }

    @Override
    public ServiceResult confirmUploadPackage(Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String uploader = (String) map.get("operator");
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
        // 2022年10月16日 14点14分，3种包状态增加CnName字段
        pendingReviewPackage.setPackageCnName(servicePackageDetailInfo.getSoftwareCnName());
        pendingReviewPackage.setPackageEnName(servicePackageDetailInfo.getSoftwareEnName());
        // 2022年10月19日 18点46分 三种包状态 增加 private String cpuRequests;private String memoryRequests
        pendingReviewPackage.setCpuRequests(servicePackageDetailInfo.getCpuRequests());
        pendingReviewPackage.setMemoryRequests(servicePackageDetailInfo.getMemoryRequests());



        Integer integer = pendingReviewPackageService.insertPendingReviewPackage(pendingReviewPackage);
        // 2022年10月12日15点16分，确认之后，详情表的isCheck属性要置为true了
        servicePackageDetailInfoService.confirmServicePackageDetailInfoById(servicePackageDetailInfoId);
        if (integer == 1){
            logger.info("资源包确认完成");
            return ServiceResult.success();
        } else {
            logger.info("资源包确认出错，pendingReview记录写入失败");
            return ServiceResult.error();
        }
    }

    @Override
    public ServiceResult cancelUploadPackage(Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        Integer servicePackageDetailInfoId = (Integer) map.get("servicePackageDetailInfoId");
        servicePackageDetailInfoService.deletePackageZipByServicePackageDetailInfoId(servicePackageDetailInfoId);
        servicePackageDetailInfoService.cancelServicePackageDetailInfoById(servicePackageDetailInfoId);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult getMyPackage(Map<String, Object> params) {
        return null;
    }
}
