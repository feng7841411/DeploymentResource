package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.BePublishedPackageMapper;
import com.feng.dao.BeRemovedPackageMapper;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.packageStatusEntity.BeRemovedPackage;
import com.feng.service.BePublishedPackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        beRemovedPackage.setBeRemovedPackageAuthor(bePublishedPackage.getBePublishedPackageName());
        beRemovedPackage.setBeRemovedPackageTime(new Date());
        beRemovedPackage.setBeRemovedPackageStatus("已下架");
        beRemovedPackage.setBeRemovedPackageType(bePublishedPackage.getBePublishedPackageType());
        beRemovedPackage.setConnectedPackageId(bePublishedPackage.getConnectedPackageId());
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


}
