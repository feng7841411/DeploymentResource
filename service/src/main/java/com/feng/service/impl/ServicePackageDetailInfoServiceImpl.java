package com.feng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.ServicePackageDetailInfoMapper;
import com.feng.entity.packageToolEntity.BasicInfoForm;
import com.feng.entity.packageToolEntity.DeveloperInfoForm;
import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.ServicePackageDetailInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author feng
 * @date 2022/10/7
 * @time 22:12
 * @apiNote
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ServicePackageDetailInfoServiceImpl extends ServiceImpl<ServicePackageDetailInfoMapper, ServicePackageDetailInfo> implements ServicePackageDetailInfoService {

    private final ServicePackageDetailInfoMapper servicePackageDetailInfoMapper;

    public ServicePackageDetailInfoServiceImpl(ServicePackageDetailInfoMapper servicePackageDetailInfoMapper) {
        this.servicePackageDetailInfoMapper = servicePackageDetailInfoMapper;
    }


    @Override
    public ServiceResult insertServicePackageDetailInfo(ServicePackageDetailInfo servicePackageDetailInfo) {
        this.servicePackageDetailInfoMapper.insert(servicePackageDetailInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult getServicePackageDetailInfoFromPackingToolJson(PackingToolInfo packingToolInfo) {
        // 从打包的JSON文件抽取
        BasicInfoForm basicInfoForm = packingToolInfo.getBasicInfoForm();
        DeveloperInfoForm developerInfoForm = packingToolInfo.getDeveloperInfoForm();

        // 往商店的详情描述类填充
        ServicePackageDetailInfo servicePackageDetailInfo = new ServicePackageDetailInfo();

        // BasicInfo
        // 中文名
        servicePackageDetailInfo.setSoftwareCnName(basicInfoForm.getSoftwareCnName());
        // 服务组名
        servicePackageDetailInfo.setSoftwareGroupName(basicInfoForm.getSoftwareGroupName());
        // 服务运行环境
        servicePackageDetailInfo.setSoftwareEnvironment(basicInfoForm.getSoftwareEnvironment());
        //服务唯一标识
        servicePackageDetailInfo.setSoftwareUniqueName(basicInfoForm.getSoftwareUniqueName());
        servicePackageDetailInfo.setDomainName(basicInfoForm.getDomainName());
        servicePackageDetailInfo.setSoftwareName(basicInfoForm.getSoftwareName());
        servicePackageDetailInfo.setServiceName(basicInfoForm.getServiceName());
        servicePackageDetailInfo.setLanguageName(basicInfoForm.getLanguageName());
        // 服务版本号
        servicePackageDetailInfo.setSoftwareVersion(basicInfoForm.getSoftwareVersion());
        servicePackageDetailInfo.setMajorVersionNumber(basicInfoForm.getMajorVersionNumber());
        servicePackageDetailInfo.setMinorVersionNumber(basicInfoForm.getMinorVersionNumber());
        servicePackageDetailInfo.setRevisionNumber(basicInfoForm.getRevisionNumber());

        // DeveloperInfo
        servicePackageDetailInfo.setDeveloperName(developerInfoForm.getDeveloperName());
        servicePackageDetailInfo.setDeveloperGroup(developerInfoForm.getDeveloperGroup());
        servicePackageDetailInfo.setDeveloperPhone(developerInfoForm.getDeveloperPhone());

        return ServiceResult.success("",servicePackageDetailInfo);
    }

    @Override
    public ServiceResult selectServicePackageDetailInfoById(Integer id) {
        ServicePackageDetailInfo servicePackageDetailInfo = servicePackageDetailInfoMapper.selectById(id);
        return ServiceResult.success("",servicePackageDetailInfo);
    }

    @Override
    public ServiceResult confirmServicePackageDetailInfoById(Integer id) {
        ServicePackageDetailInfo servicePackageDetailInfo = servicePackageDetailInfoMapper.selectById(id);
        servicePackageDetailInfo.setIsConfirm("true");
        servicePackageDetailInfoMapper.updateById(servicePackageDetailInfo);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult cancelServicePackageDetailInfoById(Integer id) {
        servicePackageDetailInfoMapper.deleteById(id);
        return ServiceResult.success();
    }
}
