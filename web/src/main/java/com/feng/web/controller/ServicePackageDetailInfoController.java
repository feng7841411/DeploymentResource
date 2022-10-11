package com.feng.web.controller;

import com.feng.entity.packageToolEntity.ServicePackageDetailInfo;
import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.impl.ServicePackageDetailInfoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/10/11
 * Time: 12:45
 * Description:
 *
 * 服务包的详情类，主要是用于查询详情表，然后返回给前端展示
 * @author feng
 */

@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/servicePackageDetailInfo")
public class ServicePackageDetailInfoController {

    private static final Logger logger = LogManager.getLogger(ServicePackageDetailInfoController.class);



    @Autowired
    protected ServicePackageDetailInfoServiceImpl servicePackageDetailInfoService;


    @PostMapping("/getServicePackageDetailInfoById")
    public Result getServicePackageDetailInfoById(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        Integer detailId = (Integer) map.get("packageConnectedDetailId");
        logger.info("根据ID查询资源服务包方法被调用, 传入的ID： " + detailId);
        ServiceResult serviceResult = servicePackageDetailInfoService.selectServicePackageDetailInfoById(detailId);
        ServicePackageDetailInfo servicePackageDetailInfo = (ServicePackageDetailInfo) serviceResult.getData();
        return Result.success("200",servicePackageDetailInfo);
    }




}
