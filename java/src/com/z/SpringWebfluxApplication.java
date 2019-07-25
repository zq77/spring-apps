package com.z;

import com.z.config.WebFluxConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

/**
 * 基于Reactor Netty实现WebFlux服务
 */
public class SpringWebfluxApplication {

  public static void main(String[] args) throws IOException {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebFluxConfig.class);
    //通过ApplicationContext创建HttpHandler
    HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
    ReactorHttpHandlerAdapter httpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
    HttpServer.create("localhost", 5000).newHandler(httpHandlerAdapter).block();
    System.in.read();
  }
}
