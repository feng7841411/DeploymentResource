package com.feng.web.controller;

import cn.hutool.core.io.FileUtil;
import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.impl.LogInfoServiceImpl;
import com.feng.service.impl.PackageServiceImpl;
import com.feng.service.impl.ServicePackageDetailInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author feng
 * @date 2022/10/4
 * @time 16:04
 * @apiNote
 *
 * 这个文件是10.4下午写的，上午解决了nginx部署跨域发布文件的问题。原来File接口有点乱，重新写一个
 *
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/Package")
public class PackageController {


    private static final Logger logger = LogManager.getLogger(PackageController.class);

    private final LogInfoServiceImpl logInfoService;


    private final ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService;

    private final PackageServiceImpl packageService;

    public PackageController(PackageServiceImpl packageService, ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService, LogInfoServiceImpl logInfoService) {
        this.packageService = packageService;
        this.servicePackageDetailInfoService = servicePackageDetailInfoService;
        this.logInfoService = logInfoService;
    }

    @PostMapping("/uploadSingle")
    public Result uploadSingle(@RequestParam("files") MultipartFile[] files){
        logger.info("");
        logger.info("扫描接收的文件数组信息：");
        logger.info(files);
        logger.info("接收文件数组：files_length = " + files.length);
        for (MultipartFile file: files) {
            logger.info("文件信息：");
            logger.info(file.getOriginalFilename());
            logger.info(file.getSize());
        }
        logger.info("扫描接收的文件数组信息完成：");
        logger.info("");
        // 逻辑处理部分，那个文件上传目前不是很熟练
        // 虽然只是单文件，但是那个传输文件的前后对接，我还是抄的一个多文件的，只是前端限制了一下文件上传的数量只能是1
        // 但是后端拿到的还是一个数组，取出数组里唯一的一个MultipartFile就是处理的目标对象
        // 1、拿到文件对象
        MultipartFile file = files[0];
        // 2、这应该是一个zip文件，这个前端暂时没管，后端打个补丁
        String fileType = FileUtil.extName(file.getOriginalFilename());
        logger.info("上传的文件类型为：" + fileType);
        String zipType = "zip";
        if (!fileType.equalsIgnoreCase(zipType)){
            return Result.error("400","文件格式不是zip");
        }
        // 3.1 先把zip包存好
        logger.info("");
        ServiceResult storeResult = packageService.storePackage(file);
        logger.info(storeResult.getMsg());
        logger.info("fileUid: " + storeResult.getData());


        // 3.2 解压，获得JSON文件名 (解压放到一个路径，拿到JSON信息，然后考虑再删掉临时文件吧）
        ServiceResult unZipResult = packageService.unZipPackage((String) storeResult.getData());

        if (!"200".equals(unZipResult.getCode())) {
            logger.error("调用unZip()失败");
            return Result.error("400","系统无法正确解压资源包");
        }

        ServiceResult readJsonResult = packageService.readJsonFileFromUnZip();
        // json读取失败
        if (!"200".equals(readJsonResult.getCode())) {
            logger.error(readJsonResult.getMsg());
            return Result.error("400",readJsonResult.getMsg());
        }

        // json读取成功
        PackingToolInfo packingToolInfo = (PackingToolInfo) readJsonResult.getData();

        // 转化为软件商店的详情描述类
        ServiceResult servicePackageDetailInfoFromPackingToolJson = servicePackageDetailInfoService.getServicePackageDetailInfoFromPackingToolJson(packingToolInfo);
        ServicePackageDetailInfo servicePackageDetailInfo = (ServicePackageDetailInfo) servicePackageDetailInfoFromPackingToolJson.getData();

        // 补充JSON里没有的字段
        servicePackageDetailInfo.setConnectedPackageUid((String) storeResult.getData());
        servicePackageDetailInfo.setConnectedPackageOriginalFileName(file.getOriginalFilename());
        long l = file.getSize() / 1024 / 1024;
        servicePackageDetailInfo.setConnectedPackageSize(String.valueOf(l) + "MB");
        servicePackageDetailInfo.setIsConfirm("false");
        // 写入数据库
        servicePackageDetailInfoService.insertServicePackageDetailInfo(servicePackageDetailInfo);
        // 主键
        //
        logger.info("详细信息：");
        logger.info(servicePackageDetailInfo.toString());
        logger.info("主键：");
        logger.info(servicePackageDetailInfo.getServicePackageDetailInfoId());


        // 3.3 根据JSON，写一条包详情记录，返回主键ID
        // 4、写入一条PendingReviewPackage记录，里面带上fileUid 和 详情记录的主键ID
        // 这个目前因为需要多一道确定，所以光是上传包，只是写详情的H2数据，同时存在一个属性isConfirm，之后好的系统机构应该是对这些记录，要去清理掉包

        // 5、清理unzip文件夹
        // 这个目前就已经可以清理的，因为目标的JSON文件已经拿到了
        packageService.clearUnZipFiles();
        // 模拟线程休眠，对包的处理，为了检测前端同步方法对蒙层的控制
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success("解析成功",servicePackageDetailInfo);
    }


    /**
     * 安装包，确认的方法
     * @param params
     * @return
     */
    @PostMapping("/confirmUploadSingle")
    public Result confirmUploadSingle(@RequestBody Map<String, Object> params) {
        ServiceResult serviceResult = packageService.confirmUploadPackage(params);
        if ("200".equals(serviceResult.getCode())) {
            // 2022年10月12日 09点14分，这边确认文件，进入待审核队列之后，写个日志记录
            logInfoService.insertUploaderConfirmLogInfo(params);
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 安装包取消的方法
     * @param params
     * @return
     */
    @PostMapping("/cancelUploadSingle")
    public Result cancelUploadSingle(@RequestBody Map<String, Object> params) {
        logInfoService.insertUploaderCancelLogInfo(params);
        // 这里因为packageService会删掉detailInfo, 如果等删完了，在下面的if里，会导致想写日志，详情记录已经消失了，会nullPointer
        ServiceResult serviceResult = packageService.cancelUploadPackage(params);
        if ("200".equals(serviceResult.getCode())) {
            return Result.success();
        } else {
            return Result.error();
        }

    }



}
