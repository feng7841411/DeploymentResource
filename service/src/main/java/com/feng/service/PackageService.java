package com.feng.service;

import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.returnClass.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

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

}
