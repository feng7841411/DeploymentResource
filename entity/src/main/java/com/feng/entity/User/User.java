package com.feng.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author feng
 * @date 2022/7/31
 * @time 15:13
 * @apiNote
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;
    private String password;
    private String role;
}
