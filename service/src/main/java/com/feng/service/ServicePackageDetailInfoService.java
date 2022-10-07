package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.ServiceResult;

/**
 * @author feng
 * @date 2022/10/7
 * @time 22:01
 * @apiNote
 */
public interface ServicePackageDetailInfoService extends IService<ServicePackageDetailInfo> {


    /**
     * 插入记录
     * @param servicePackageDetailInfo
     * @return
     */
    public ServiceResult insertServicePackageDetailInfo(ServicePackageDetailInfo servicePackageDetailInfo);

    /**
     * 从打包工具的描述文件，转化为商店的详情描述类ServicePackageDetailInfo
     * @param packingToolInfo
     * @return
     */
    public ServiceResult getServicePackageDetailInfoFromPackingToolJson(PackingToolInfo packingToolInfo);


}
