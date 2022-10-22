package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.BePublishedPackageMapper;
import com.feng.dao.BeRemovedPackageMapper;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.packageStatusEntity.BeRemovedPackage;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.BePublishedPackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 15:04
 * Description:
 *
 * @author feng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BePublishedPackageServiceImpl extends ServiceImpl<BePublishedPackageMapper, BePublishedPackage> implements BePublishedPackageService {

    private final BePublishedPackageMapper bePublishedPackageMapper;

    private final BeRemovedPackageMapper removedPackageMapper;


    public BePublishedPackageServiceImpl(BePublishedPackageMapper bePublishedPackageMapper, BeRemovedPackageMapper removedPackageMapper) {
        this.bePublishedPackageMapper = bePublishedPackageMapper;
        this.removedPackageMapper = removedPackageMapper;
    }


    @Override
    public Integer removePackageById(Integer id) {
        // 从已发布的表里查出来，然后删除，把信息存入下架的表
        BePublishedPackage bePublishedPackage = bePublishedPackageMapper.selectById(id);
        bePublishedPackageMapper.deleteById(id);
        // 写入新表
        BeRemovedPackage beRemovedPackage = new BeRemovedPackage();
        beRemovedPackage.setBeRemovedPackageName(bePublishedPackage.getBePublishedPackageName());
        beRemovedPackage.setBeRemovedPackageAuthor(bePublishedPackage.getBePublishedPackageAuthor());
        beRemovedPackage.setBeRemovedPackageTime(new Date());
        beRemovedPackage.setBeRemovedPackageStatus("已下架");
        beRemovedPackage.setBeRemovedPackageType(bePublishedPackage.getBePublishedPackageType());
        beRemovedPackage.setConnectedPackageUid(bePublishedPackage.getConnectedPackageUid());
        beRemovedPackage.setConnectedDetailInfoId(bePublishedPackage.getConnectedDetailInfoId());
        // 2022年10月16日 14点18分 3种状态增加CnName字段
        beRemovedPackage.setPackageCnName(bePublishedPackage.getPackageCnName());
        // 2022年10月19日 18点51分 增加容器服务的cpu memory 的字段
        beRemovedPackage.setCpuRequests(bePublishedPackage.getCpuRequests());
        beRemovedPackage.setMemoryRequests(bePublishedPackage.getMemoryRequests());

        int insert = removedPackageMapper.insert(beRemovedPackage);
        return insert;
    }

    @Override
    public Integer deleteAllPublishedPackage() {
        QueryWrapper<BePublishedPackage> bePublishedPackageQueryWrapper = new QueryWrapper<>();
        int delete = bePublishedPackageMapper.delete(bePublishedPackageQueryWrapper);
        return delete;
    }


    /**
     * 2022年9月24日 09点58分
     * 虽然这个目前只有 正常这一种状态，但是还是 写上了，加了一个列筛选，状态需要是“正常”
     * @return
     */
    @Override
    public Integer getPublishedPackageAsideNumber() {
        QueryWrapper<BePublishedPackage> bePublishedPackageQueryWrapper = new QueryWrapper<>();
        //
        bePublishedPackageQueryWrapper.eq("BE_PUBLISHED_PACKAGE_STATUS","正常");
        Integer integer = bePublishedPackageMapper.selectCount(bePublishedPackageQueryWrapper);
        return integer;
    }

    @Override
    public ServiceResult selectBePublishedPackageByAuthor(String author) {
        QueryWrapper<BePublishedPackage> bePublishedPackageQueryWrapper = new QueryWrapper<>();
        bePublishedPackageQueryWrapper.select("BE_PUBLISHED_PACKAGE_NAME",
                "BE_PUBLISHED_PACKAGE_Type",
                "BE_PUBLISHED_PACKAGE_AUTHOR",
                "BE_PUBLISHED_PACKAGE_TIME",
                "BE_PUBLISHED_PACKAGE_STATUS",
                "CONNECTED_PACKAGE_UID",
                "CONNECTED_DETAIL_INFO_ID").eq("BE_PUBLISHED_PACKAGE_AUTHOR",author);
        List<BePublishedPackage> bePublishedPackages = bePublishedPackageMapper.selectList(bePublishedPackageQueryWrapper);
        return ServiceResult.success("200",bePublishedPackages);
    }

    /**
     * 全查询
     * @return
     */
    @Override
    public ServiceResult getAllBePublishedPackageInfo() {
        QueryWrapper<BePublishedPackage> bePublishedPackageQueryWrapper = new QueryWrapper<>();
        List<BePublishedPackage> bePublishedPackages = bePublishedPackageMapper.selectList(bePublishedPackageQueryWrapper);
        return ServiceResult.success("200",bePublishedPackages);
    }


}
