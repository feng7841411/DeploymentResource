package com.feng.service;

import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.returnClass.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author feng
 * @date 2022/10/4
 * @time 17:26
 * @apiNote
 */


public interface PackageService {


    /**
     * 把上传的ZIP包放到到file文件夹，但是不需要写fileInfo这种数据库记录，这个等【待审核】的那个表来写
     * 这里提供uid+文件类型就可以了
     * @param file
     * @return
     */
    public ServiceResult storePackage(MultipartFile file);


    /**
     * 资源包解压输出方法
     * @param fileUid
     * @return
     */
    public ServiceResult unZipPackage(String fileUid);

    /**
     * 从磁盘读JSON文件，尝试拿到信息
     * @return
     */
    public ServiceResult readJsonFileFromUnZip();


    /**
     * 把打包工具里那个复杂的JSON文件，读取必要的字段，转化写入一张软件商店的单表，插入一条记录，返回主键
     * @param packingToolInfo
     * @return
     */
    public ServiceResult getInfoFromPackingToolInfo(PackingToolInfo packingToolInfo);


    /**
     * 善后方法，解压，拿到JSON处理以后，把存放unzip的文件夹清空掉
     * @return
     */
    public ServiceResult clearUnZipFiles();

    /**
     * 确认资源包
     * @return
     */
    public ServiceResult confirmUploadPackage(Map<String, Object> params);


    /**
     * 取消安装包上传
     * @return
     */
    public ServiceResult cancelUploadPackage(Map<String, Object> params);


    /**
     * 前端，【我的资源方法】传输一个人名，从3张表里查Author是这个人的Package,返回
     * @param params
     * @return
     */
    public ServiceResult getMyPackage(Map<String, Object> params);



}
