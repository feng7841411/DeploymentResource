package com.feng.web.controller;

import com.feng.entity.returnClass.Result;
import com.feng.entity.User.UserInfo;
import com.feng.service.impl.UserInfoServiceImpl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/15
 * Time: 10:49
 * Description:
 *
 * @author feng
 */
@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/user")
public class UserController {


    private final UserInfoServiceImpl userInfoService;

    public UserController(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/register")
    public Result registerUserInfo(@RequestBody Map<String, Object> params){
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        HashMap<String, String> registerForm = (HashMap<String, String>) map.get("registerForm");
        String newUserName = registerForm.get("newUserName");
        String newPassword =registerForm.get("newPassword");
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(newUserName);
        userInfo.setPassword(newPassword);
        userInfo.setRole("developer");
        Boolean aBoolean = userInfoService.registerUser(userInfo);
        System.out.println("aBooleadn: " + aBoolean);
        if (Boolean.TRUE.equals(aBoolean)) {
            return Result.success("注册成功，用户权限为：开发者", "");
        } else {
            return Result.error("600", "注册失败，可能存在同名用户");
        }
    }

    @PostMapping("/login")
    public Result loginUserInfo(@RequestBody UserInfo userInfo) {
        // userInfo1 从数据库里出来，是有role信息的
        UserInfo userInfo1 = userInfoService.logInUser(userInfo);
        if (userInfo1 != null) {
            String msg = "登录成功，用户：" + userInfo1.getUsername() + " 登录身份：" + userInfo1.getRole();
            return Result.success(msg, userInfo1);
        } else {
            return Result.error("403", "登录失败");
        }
    }

    @PostMapping("/destroy")
    public Result destroyUserInfo(@RequestParam Integer userInfoId) {
        Boolean aBoolean = userInfoService.destroyUser(userInfoId);
        if (Boolean.TRUE.equals(aBoolean)) {
            return Result.success("账号已注销", "");
        } else {
            return Result.error("403", "想注销的账号不存在");
        }
    }


}
