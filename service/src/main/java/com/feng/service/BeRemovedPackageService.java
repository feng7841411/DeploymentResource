package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.packageStatusEntity.BeRemovedPackage;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 14:59
 * Description:
 *
 * @author feng
 */

public interface BeRemovedPackageService extends IService<BeRemovedPackage> {


    /**
     * 把下架的服务彻底删除
     * @return
     */
    public Integer deleteRemovedPackageById(Integer id);

    /**
     * 危险，清表操作
     * @return
     */
    public Integer deleteAllRemovedPackage();


    public Integer publishPackageAgainst(Integer id);


    /**
     * 侧边栏数字展示
     * @return
     */
    public Integer getRemovedPackageAsideNumber();


}
