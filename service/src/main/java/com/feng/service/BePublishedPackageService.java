package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.returnClass.ServiceResult;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 14:53
 * Description:
 *
 * @author feng
 */
public interface BePublishedPackageService extends IService<BePublishedPackage> {


    /**
     * 根据ID删除资源包
     * @param id
     * @return
     */
    public Integer removePackageById(Integer id);


    /**
     * 危险，清表操作
     * @return
     */
    public Integer deleteAllPublishedPackage();


    /**
     * 查询已上架的包的数量，用于侧标栏目显示，这个因为目前在已发布的包里只有一种状态
     * 所有其实很简单， 就是普通的全表count
     * @return
     */
    public Integer getPublishedPackageAsideNumber();


    /**
     * 根据Author字段筛选已经上传的资源包
     * @param author
     * @return
     */
    public ServiceResult selectBePublishedPackageByAuthor(String author);


    public ServiceResult getAllBePublishedPackageInfo();

}
