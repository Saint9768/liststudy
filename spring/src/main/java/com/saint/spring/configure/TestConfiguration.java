package com.saint.spring.configure;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-18 7:56
 */
@Configuration
public class TestConfiguration {
    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event) {
        System.out.println("当前 WebServer 实现类为：" + event.getWebServer().getClass().getName());
    }
}
