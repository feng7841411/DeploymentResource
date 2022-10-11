package com.feng.web.controller;

import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.impl.MyResourcesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author feng
 * @date 2022/10/10
 * @time 22:37
 * @apiNote
 */

@RestController
@RequestMapping("/v1/myResources")
public class MyResourcesController {


    private final MyResourcesServiceImpl myResourcesService;


    public MyResourcesController(MyResourcesServiceImpl myResourcesService) {
        this.myResourcesService = myResourcesService;
    }


    @PostMapping("/getMyResources")
    public Result getMyResources(@RequestBody Map<String, Object> params) {
        ServiceResult myResources = myResourcesService.getMyResources(params);
        return Result.success("200",myResources.getData());
    }


}
