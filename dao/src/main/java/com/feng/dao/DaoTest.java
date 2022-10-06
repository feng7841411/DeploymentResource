package com.feng.dao;

import com.feng.entity.EntityTest;

/**
 * @author feng
 * @date 2022/9/10
 * @time 16:19
 * @apiNote
 */
public class DaoTest {
    public String showDao(){
        System.out.println();
        EntityTest entityTest = new EntityTest();
        return "这里是Dao Module，可以依赖Entity" + entityTest.showEntity();
    }
}
