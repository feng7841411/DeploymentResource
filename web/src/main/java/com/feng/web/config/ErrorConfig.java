package com.feng.web.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author: 冯金河
 * @Date: 2022/10/25 10:43
 * @Description:
 *
 * 2022年10月25日 10点45分
 * 解决Vue和SpringBoot整合时候的刷新问题
 * 因为这样子，前端没有类似nginx这样的服务器运行
 * 所以没有这个的话，在非Index的页面刷新，浏览器的Url定位不到资源，就报错
 * 加了这个，我理解是出现这种情况的时候，重定位会index
 * 我以为main部分会回到Index，但是试了一下，只有侧边栏收起，main竟然不会变
 * 算是暂时解决了前后端整合部署的一个小问题
 */
@Component
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void  registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
        registry.addErrorPages(error404Page);
    }
}