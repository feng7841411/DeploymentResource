package com.feng.publicData.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.publicDataDao.PublicDataMapper;
import com.feng.entity.publicData.PublicData;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.publicData.PublicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 冯金河
 * @Date: 2022/10/28 18:53
 * @Description:
 */

@Service
public class PublicDataServiceImpl extends ServiceImpl<PublicDataMapper, PublicData> implements PublicDataService {


    private final PublicDataMapper publicDataMapper;

    public PublicDataServiceImpl(PublicDataMapper publicDataMapper) {
        this.publicDataMapper = publicDataMapper;
    }

    @Override
    public ServiceResult insertPublicData(PublicData publicData) {
        publicDataMapper.insert(publicData);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult getAllPublicData() {
        QueryWrapper<PublicData> publicDataQueryWrapper = new QueryWrapper<>();
        List<PublicData> publicDataList = publicDataMapper.selectList(publicDataQueryWrapper);
        return ServiceResult.success("查询public表全部数据",publicDataList);
    }
}
