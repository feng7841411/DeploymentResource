package com.feng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.entity.User.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/15
 * Time: 8:58
 * Description:
 *
 * @author feng
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
