package com.feng.service.impl;

import com.feng.entity.myResources.MyResources;
import com.feng.entity.packageStatusEntity.BePublishedPackage;
import com.feng.entity.packageStatusEntity.BeRemovedPackage;
import com.feng.entity.packageStatusEntity.PendingReviewPackage;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.MyResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author feng
 * @date 2022/10/10
 * @time 16:27
 * @apiNote
 */
@Service
public class MyResourcesServiceImpl implements MyResourcesService {


    private final PendingReviewPackageServiceImpl pendingReviewPackageService;

    private final BePublishedPackageServiceImpl bePublishedPackageService;

    private final BeRemovedPackageServiceImpl beRemovedPackageService;

    public MyResourcesServiceImpl(PendingReviewPackageServiceImpl pendingReviewPackageService, BePublishedPackageServiceImpl bePublishedPackageService, BeRemovedPackageServiceImpl beRemovedPackageService) {
        this.pendingReviewPackageService = pendingReviewPackageService;
        this.bePublishedPackageService = bePublishedPackageService;
        this.beRemovedPackageService = beRemovedPackageService;
    }


    /**
     * 因为不想重复弄一张表，所有这块设计是，做一个统一实体类，从3张状态表里拿去信息，汇合成一个数组返回
     *
     * 其实3张表的设计，我觉得也是合理的，因为目前3张表的字段很相似，但是考虑之后的扩展性，3张表的单独，各自扩展比较好
     * @param params
     * @return
     */
    @Override
    public ServiceResult getMyResources(Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        String author = (String) map.get("author");
        LinkedList<MyResources> myResources = new LinkedList<>();

        // pending
        ArrayList<PendingReviewPackage> pendingReviewPackages = (ArrayList<PendingReviewPackage>) pendingReviewPackageService.selectPendingReviewPackageByAuthor(author).getData();
        for (PendingReviewPackage pendingReviewPackage : pendingReviewPackages) {
            MyResources myResources1 = new MyResources();
            myResources1.setPackageName(pendingReviewPackage.getPendingReviewPackageName());
            myResources1.setPackageType(pendingReviewPackage.getPendingReviewPackageType());
            myResources1.setPackageTime(pendingReviewPackage.getPendingReviewPackageTime());
            myResources1.setPackageStatus(pendingReviewPackage.getPendingReviewPackageStatus());
            myResources1.setConnectedPackageUid(pendingReviewPackage.getConnectedPackageUid());
            myResources1.setConnectedDetailInfoId(pendingReviewPackage.getConnectedDetailInfoId());
            myResources.add(myResources1);
        }

        // bePublished
        ArrayList<BePublishedPackage> bePublishedPackages = (ArrayList<BePublishedPackage>) bePublishedPackageService.selectBePublishedPackageByAuthor(author).getData();
        for (BePublishedPackage bePublishedPackage : bePublishedPackages) {
            MyResources myResources1 = new MyResources();
            myResources1.setPackageName(bePublishedPackage.getBePublishedPackageName());
            myResources1.setPackageType(bePublishedPackage.getBePublishedPackageType());
            myResources1.setPackageTime(bePublishedPackage.getBePublishedPackageTime());
            myResources1.setPackageStatus(bePublishedPackage.getBePublishedPackageStatus());
            myResources1.setConnectedPackageUid(bePublishedPackage.getConnectedPackageUid());
            myResources1.setConnectedDetailInfoId(bePublishedPackage.getConnectedDetailInfoId());
            myResources.add(myResources1);
        }

        // beRemoved
        ArrayList<BeRemovedPackage> beRemovedPackages = (ArrayList<BeRemovedPackage>) beRemovedPackageService.selectBeRemovedPackageByAuthor(author).getData();
        for (BeRemovedPackage beRemovedPackage: beRemovedPackages) {
            MyResources myResources1 = new MyResources();
            myResources1.setPackageName(beRemovedPackage.getBeRemovedPackageName());
            myResources1.setPackageType(beRemovedPackage.getBeRemovedPackageType());
            myResources1.setPackageTime(beRemovedPackage.getBeRemovedPackageTime());
            myResources1.setPackageStatus(beRemovedPackage.getBeRemovedPackageStatus());
            myResources1.setConnectedPackageUid(beRemovedPackage.getConnectedPackageUid());
            myResources1.setConnectedDetailInfoId(beRemovedPackage.getConnectedDetailInfoId());
            myResources.add(myResources1);
        }
        return ServiceResult.success("200",myResources);
    }



}
