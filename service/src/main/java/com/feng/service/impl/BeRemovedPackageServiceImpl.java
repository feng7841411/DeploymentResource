package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.BePublishedPackageMapper;
import com.feng.dao.BeRemovedPackageMapper;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.packageStatusEntity.BeRemovedPackage;
import com.feng.service.BeRemovedPackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 15:06
 * Description:
 *
 * @author feng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BeRemovedPackageServiceImpl extends ServiceImpl<BeRemovedPackageMapper, BeRemovedPackage> implements BeRemovedPackageService {


    private final BeRemovedPackageMapper beRemovedPackageMapper;

    private final BePublishedPackageMapper bePublishedPackageMapper;

    public BeRemovedPackageServiceImpl(BeRemovedPackageMapper beRemovedPackageMapper, BePublishedPackageMapper bePublishedPackageMapper) {
        this.beRemovedPackageMapper = beRemovedPackageMapper;
        this.bePublishedPackageMapper = bePublishedPackageMapper;
    }



    /**
     * 根据ID把下架的服务彻底删除
     * @return
     */
    @Override
    public Integer deleteRemovedPackageById(Integer id) {
        int i = beRemovedPackageMapper.deleteById(id);
        // 等关联了具体包之后，这里除了修改数据表，还应该去做文件删除操作
        return i;
    }

    /**
     * 清表，估计也就是测试用
     * @return
     */
    @Override
    public Integer deleteAllRemovedPackage() {
        QueryWrapper<BeRemovedPackage> beRemovedPackageQueryWrapper = new QueryWrapper<>();
        int delete = beRemovedPackageMapper.delete(beRemovedPackageQueryWrapper);
        return delete;
    }


    /**
     * 把下架的包重新上架
     * @return
     */
    @Override
    public Integer publishPackageAgainst(Integer id) {
        // 从被下架的表里读出来
        BeRemovedPackage beRemovedPackage = beRemovedPackageMapper.selectById(id);
        beRemovedPackageMapper.deleteById(id);
        // 重新上架
        BePublishedPackage bePublishedPackage = new BePublishedPackage();
        bePublishedPackage.setBePublishedPackageName(beRemovedPackage.getBeRemovedPackageName());
        bePublishedPackage.setBePublishedPackageAuthor(beRemovedPackage.getBeRemovedPackageAuthor());
        bePublishedPackage.setBePublishedPackageTime(new Date());
        bePublishedPackage.setConnectedPackageUid(beRemovedPackage.getConnectedPackageUid());
        bePublishedPackage.setBePublishedPackageType(beRemovedPackage.getBeRemovedPackageType());
        bePublishedPackage.setBePublishedPackageStatus("正常");
        int insert = bePublishedPackageMapper.insert(bePublishedPackage);
        return insert;
    }

    @Override
    public Integer getRemovedPackageAsideNumber() {
        QueryWrapper<BeRemovedPackage> beRemovedPackageQueryWrapper = new QueryWrapper<>();
        return beRemovedPackageMapper.selectCount(beRemovedPackageQueryWrapper);
    }
}
