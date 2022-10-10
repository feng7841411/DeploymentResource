package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.BePublishedPackageMapper;
import com.feng.dao.PendingReviewPackageMapper;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.packageStatusEntity.PendingReviewPackage;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.PendingReviewPackageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 15:02
 * Description:
 *
 * @author feng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PendingReviewPackageServiceImpl extends ServiceImpl<PendingReviewPackageMapper, PendingReviewPackage> implements PendingReviewPackageService {


    private static final Logger logger = LogManager.getLogger(PendingReviewPackageServiceImpl.class.getName());

    private final PendingReviewPackageMapper pendingReviewPackageMapper;

    private final BePublishedPackageMapper bePublishedPackageMapper;

    public PendingReviewPackageServiceImpl(PendingReviewPackageMapper pendingReviewPackageMapper, BePublishedPackageMapper bePublishedPackageMapper) {
        this.pendingReviewPackageMapper = pendingReviewPackageMapper;
        this.bePublishedPackageMapper = bePublishedPackageMapper;
    }

    @Override
    public Integer insertPendingReviewPackage(PendingReviewPackage pendingReviewPackage) {
        if (pendingReviewPackage == null) {
            logger.warn("尝试写入一条待审核资源记录，但是参数为NULL，写入失败");
            return 0;
        }else {
            int insert = pendingReviewPackageMapper.insert(pendingReviewPackage);
            return insert;
        }

    }

    @Override
    public PendingReviewPackage selectPendingReviewPackageById(Integer id) {
        PendingReviewPackage pendingReviewPackage = pendingReviewPackageMapper.selectById(id);
        if (pendingReviewPackage == null) {
            logger.error("尝试通过ID查询待审核的资源包，但是传入的ID不在数据库中");
        } else {
            logger.info("通过ID查询待审核资源成功。");
        }
        return pendingReviewPackage;
    }

    @Override
    public Integer ignorePendingReviewPackageById(Integer id) {
        PendingReviewPackage pendingReviewPackage = new PendingReviewPackage();
        pendingReviewPackage.setPendingReviewPackageStatus("已忽略");
        return pendingReviewPackageMapper.update(pendingReviewPackage,
                new QueryWrapper<PendingReviewPackage>().eq("PENDING_REVIEW_PACKAGE_ID",id));
    }

    @Override
    public Integer refusePendingReviewPackageById(Integer id) {
        PendingReviewPackage pendingReviewPackage = new PendingReviewPackage();
        pendingReviewPackage.setPendingReviewPackageStatus("已拒绝");
        return pendingReviewPackageMapper.update(pendingReviewPackage,
                new QueryWrapper<PendingReviewPackage>().eq("PENDING_REVIEW_PACKAGE_ID",id));

    }

    @Override
    public Integer checkPendingReviewPackageById(Integer id) {
        // 理论上这里应该引接漏洞扫描之类的组件
        // 现在只是改一下状态，只要这个ID存在，必定返回1，标识通过了扫描
        PendingReviewPackage pendingReviewPackage = new PendingReviewPackage();
        pendingReviewPackage.setPendingReviewPackageStatus("待发布");
        return pendingReviewPackageMapper.update(pendingReviewPackage,
                new QueryWrapper<PendingReviewPackage>().eq("PENDING_REVIEW_PACKAGE_ID",id));
    }

    @Override
    public Integer publishPendingReviewPackageById(Integer id) {
        // 发布，在待审核表里删除，在已上架的表里插入一条记录
        // 读出，删除原始记录
        PendingReviewPackage pendingReviewPackage = pendingReviewPackageMapper.selectById(id);
        pendingReviewPackageMapper.deleteById(id);
        // 写入新纪录
        BePublishedPackage bePublishedPackage = new BePublishedPackage();
        bePublishedPackage.setBePublishedPackageName(pendingReviewPackage.getPendingReviewPackageName());
        bePublishedPackage.setBePublishedPackageAuthor(pendingReviewPackage.getPendingReviewPackageAuthor());
        bePublishedPackage.setBePublishedPackageTime(new Date());
        bePublishedPackage.setBePublishedPackageStatus("正常");
        bePublishedPackage.setBePublishedPackageType(pendingReviewPackage.getPendingReviewPackageType());
        // 记得这个关联的资源包ID也要转过来
        bePublishedPackage.setConnectedPackageUid(pendingReviewPackage.getConnectedPackageUid());
        bePublishedPackage.setConnectedDetailInfoId(pendingReviewPackage.getConnectedDetailInfoId());
        return bePublishedPackageMapper.insert(bePublishedPackage);
    }

    @Override
    public Integer deletePendingReviewPackageById(Integer id) {
        return bePublishedPackageMapper.deleteById(id);
    }

    @Override
    public Integer deleteAllPendingReviewPackage() {
        QueryWrapper<PendingReviewPackage> pendingReviewPackageQueryWrapper = new QueryWrapper<>();
        int delete = pendingReviewPackageMapper.delete(pendingReviewPackageQueryWrapper);
        return delete;
    }

    @Override
    public Integer generateSomePendingReviewPackage() {
        String[] names = {"测试资源包1","测试资源包2","测试资源包3","计算服务","工具服务"};
        String[] authors = {"测试人员","徐恒","张朋","系统管理员","测试账号A"};
        int count = 0;
        for (int i = 0; i < 5; i++) {
            PendingReviewPackage pendingReviewPackage = new PendingReviewPackage();
            pendingReviewPackage.setPendingReviewPackageName(names[i]);
            pendingReviewPackage.setPendingReviewPackageAuthor(authors[i]);
            pendingReviewPackage.setPendingReviewPackageTime(new Date());
            pendingReviewPackage.setPendingReviewPackageStatus("待审核");
            pendingReviewPackage.setPendingReviewPackageType("容器镜像服务");
            count = count + pendingReviewPackageMapper.insert(pendingReviewPackage);
        }
        return count;
    }

    @Override
    public Integer getPendingReviewAsideNumber() {
        QueryWrapper<PendingReviewPackage> pendingReviewPackageQueryWrapper = new QueryWrapper<>();
        pendingReviewPackageQueryWrapper.eq("PENDING_REVIEW_PACKAGE_STATUS","待审核");
        return pendingReviewPackageMapper.selectCount(pendingReviewPackageQueryWrapper);
    }

    @Override
    public ServiceResult selectPendingReviewPackageByAuthor(String author) {
        QueryWrapper<PendingReviewPackage> pendingReviewPackageQueryWrapper = new QueryWrapper<>();
        pendingReviewPackageQueryWrapper.select("PENDING_REVIEW_PACKAGE_NAME",
                "PENDING_REVIEW_PACKAGE_Type",
                "PENDING_REVIEW_PACKAGE_AUTHOR",
                "PENDING_REVIEW_PACKAGE_TIME",
                "PENDING_REVIEW_PACKAGE_STATUS",
                "CONNECTED_PACKAGE_UID",
                "CONNECTED_DETAIL_INFO_ID").eq("PENDING_REVIEW_PACKAGE_AUTHOR",author);
        List<PendingReviewPackage> pendingReviewPackages = pendingReviewPackageMapper.selectList(pendingReviewPackageQueryWrapper);
        return ServiceResult.success("200",pendingReviewPackages);
    }
}
