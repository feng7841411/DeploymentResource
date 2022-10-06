package com.feng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.entity.packageStatusEntity.PendingReviewPackage;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 14:34
 * Description:
 *
 * @author feng
 */
@Mapper
public interface PendingReviewPackageMapper extends BaseMapper<PendingReviewPackage> {
}
