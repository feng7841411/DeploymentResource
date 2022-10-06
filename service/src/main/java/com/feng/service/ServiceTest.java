package com.feng.service;

import com.feng.dao.DaoTest;

/**
 * @author feng
 * @date 2022/9/10
 * @time 16:17
 * @apiNote
 */
public class ServiceTest {
    public String showService(){
        DaoTest daoTest = new DaoTest();
        return "这里是service module, 可以依赖dao" + daoTest.showDao();
    }
}
