package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.packageStatusEntity.PendingReviewPackage;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/20
 * Time: 14:45
 * Description:
 *
 * @author feng
 */

public interface PendingReviewPackageService extends IService<PendingReviewPackage> {

    /**
     * 普通的插入操作
     * @param pendingReviewPackage
     * @return
     */
    public Integer insertPendingReviewPackage(PendingReviewPackage pendingReviewPackage);

    /**
     * 根据ID读
     * @param id
     * @return
     */
    public PendingReviewPackage selectPendingReviewPackageById(Integer id);

    /**
     * 忽略掉
     * @param id
     * @return
     */
    public Integer ignorePendingReviewPackageById(Integer id);

    /**
     * 拒接掉
     * @param id
     * @return
     */
    public Integer refusePendingReviewPackageById(Integer id);


    /**
     * 检查、漏洞扫描、杀毒
     * @param id
     * @return
     */
    public Integer checkPendingReviewPackageById(Integer id);


    /**
     * 上架
     * @param id
     * @return
     */
    public Integer publishPendingReviewPackageById(Integer id);

    /**
     * 单条删除方法
     * @param id
     * @return
     */
    public Integer deletePendingReviewPackageById(Integer id);

    /**
     * 【危险】清空表
     * @return
     */
    public Integer deleteAllPendingReviewPackage();


    /**
     * 生成几条记录，不关联实际的包
     * @return
     */
    public Integer generateSomePendingReviewPackage();


    /**
     * 2022年9月24日时间
     * 查询所有未审核的记录的数量，用于前端在Aside能够显示数字
     * @return
     */
    public Integer getPendingReviewAsideNumber();


}
