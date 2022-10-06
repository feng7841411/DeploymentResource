package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.User.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/15
 * Time: 8:59
 * Description:
 *
 * @author feng
 */
public interface UserInfoService extends IService<UserInfo> {


    /**
     * 注册接口
     * 注意要查询接口里面有没有已有的账号
     * 好像只有这种情况是失败的
     * @param userInfo
     * @return
     */
    public Boolean registerUser(UserInfo userInfo);

    /**
     * 销毁账号
     * @param userInfoId
     * @return
     */

    public Boolean destroyUser(Integer userInfoId);

    /**
     * 登录方法，传入一个只用用户名密码的UserInfo
     * 查询有没有，如果有拼接角色信息返回
     * 没有的话，返回空
     * @param userInfo
     * @return
     */
    public UserInfo logInUser(UserInfo userInfo);





}
