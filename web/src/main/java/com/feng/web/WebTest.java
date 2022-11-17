package com.feng.web;

import com.feng.service.ServiceTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author feng
 * @date 2022/9/10
 * @time 16:15
 * @apiNote
 */
@RestController
@RequestMapping("/v1/test")
public class WebTest {

    /**
     * 测试方法，测试多Module依赖传递和整个工程运行情况
     *
     * @return
     */
    @RequestMapping("/showAll")
    public String showAll() {
        ServiceTest serviceTest = new ServiceTest();
        return "这里是web module，引入了service依赖" + serviceTest.showService();
    }
}

