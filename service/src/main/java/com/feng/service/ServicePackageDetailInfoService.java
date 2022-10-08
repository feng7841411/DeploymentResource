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


    /**
     * 用于资源包确认的时候，根据ID查询详情，里面有些信息用于写一条pendingReviewPackage
     * @param id
     * @return
     */
    public ServiceResult selectServicePackageDetailInfoById(Integer id);


    /**
     * 确认资源包，把对应的ID的isConfirm改成true
     * @param id
     * @return
     */
    public ServiceResult confirmServicePackageDetailInfoById(Integer id);


    /**
     * 资源包取消，直接把那条记录删了就行了
     * @param id
     * @return
     */
    public ServiceResult cancelServicePackageDetailInfoById(Integer id);



}
