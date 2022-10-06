package com.feng.entity.User;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/15
 * Time: 8:54
 * Description:
 *
 * @author feng
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/6/24
 * Time: 10:38
 * Description:
 *
 * 注意这里的User指的是系统登录的时候，用的test、admin这种用户
 * @author feng
 */

@Entity(name = "USER_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @TableId(type = IdType.AUTO, value = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer userId;

    private String username;

    private String password;

    /**
     * 角色字段
     * 管理员：admin
     * 开发者：developer
     */
    private String role;

    /**
     * 这里就简单弄一个登录，然后暂时不引入权限控制，
     * 权限控制不应该是自己写角色类型字段来实现，
     * 真要做应该引入用户权限管理的框架
     */



}

