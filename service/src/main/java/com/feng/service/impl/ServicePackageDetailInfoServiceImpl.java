package com.feng.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.ServicePackageDetailInfoMapper;
import com.feng.entity.packageToolEntity.BasicInfoForm;
import com.feng.entity.packageToolEntity.DeveloperInfoForm;
import com.feng.entity.packageToolEntity.PackingToolInfo;
import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.ServicePackageDetailInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @author feng
 * @date 2022/10/7
 * @time 22:12
 * @apiNote
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ServicePackageDetailInfoServiceImpl extends ServiceImpl<ServicePackageDetailInfoMapper, ServicePackageDetailInfo> implements ServicePackageDetailInfoService {



    @Value("${files.upload.path}")
    private String fileUploadPath;

    private final ServicePackageDetailInfoMapper servicePackageDetailInfoMapper;

    private static final Logger logger = LogManager.getLogger(ServicePackageDetailInfoServiceImpl.class);


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
        // 英文名
        servicePackageDetailInfo.setSoftwareEnName(basicInfoForm.getServiceName());
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

        // 2022年10月19日 18点27分 增加容器服务的cpu和memory信息
        servicePackageDetailInfo.setCpuRequests(basicInfoForm.getCpuRequests());
        servicePackageDetailInfo.setMemoryRequests(basicInfoForm.getMemoryRequests());


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

    @Override
    public ServiceResult deletePackageZipByServicePackageDetailInfoId(Integer id) {
        ServicePackageDetailInfo servicePackageDetailInfo = servicePackageDetailInfoMapper.selectById(id);
        String connectedPackageUid = servicePackageDetailInfo.getConnectedPackageUid();
        logger.info("资源包取消了，尝试删除zip： " + connectedPackageUid);
        //File uploadFile = new File(fileUploadPath + '/' + fileUid);
        File file = new File(fileUploadPath + '/' + connectedPackageUid);
        if ( !file.exists()) {
            logger.info("没有找到目标文件： " + file.getPath());
            return ServiceResult.error("尝试删除zip失败，没有对应文件","");
        } else {
            boolean del = FileUtil.del(file);
            if (del) {
                logger.info("目标zip已经被删除");
                return ServiceResult.success();
            } else {
                logger.warn("尝试删除zip，文件存在，但是删除失败了");
                return ServiceResult.error("尝试删除zip，文件存在，但是删除失败了","");
            }
        }

    }
}
