package com.feng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.UserInfoMapper;
import com.feng.entity.User.UserInfo;
import com.feng.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/15
 * Time: 9:24
 * Description:
 *
 * @author feng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }


    @Override
    public Boolean registerUser(UserInfo userInfo) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.select().eq("USERNAME", userInfo.getUsername());
        List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
        if (userInfos.size() != 0) {
            // 输入的注册用户名已经存在
            return false;
        } else {
            // 注册正常，写入一条数据，默认允许注册的都是开发者用户
            userInfo.setRole("developer");
            userInfoMapper.insert(userInfo);
            return true;
        }

    }

    @Override
    public Boolean destroyUser(Integer userInfoId) {
        // 传入一个用户的主键，如果有就删除掉，没有这个用户主键实际上不存在，返回false
        UserInfo userInfo = userInfoMapper.selectById(userInfoId);
        if (userInfo != null){
            userInfoMapper.deleteById(userInfoId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserInfo logInUser(UserInfo userInfo) {
        // 登录接口，校验一下用户名密码是否正确，如果是正确的，加上Role信息返回
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.select().eq("USERNAME",userInfo.getUsername()).eq("PASSWORD",userInfo.getPassword());
        List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryWrapper);
        if (userInfos.size() == 0) {
            // 数据库没有这个用户名密码的用户
            return null;
        } else {
            return userInfos.get(0);
        }

    }
}
