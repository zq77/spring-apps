package com.z.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启WebSocket支持
 * 自动注册使用了@ServerEndpoint 注解声明的Websocket endpoint
 * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

//    WebSocket 启动的时候优先于 spring 容器，从而导致在 WebSocketServer 中调用业务Service会报空指针异常。
//    所以需要在 WebSocketServer 中将所需要用到的 service 给静态初始化一下
//    @Autowired
//    public void setRedisService(T service) {
//        WebSocketServer.service = service;
//    }
}
