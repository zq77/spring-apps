package com.z.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebHandler;

@Configuration
@ComponentScan
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
//  @Bean
//  public WebHandler webHandler(ApplicationContext applicationContext) {
//    return new DispatcherHandler(applicationContext);
//  }

  /**
   * add static resource url
   */
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/resources/**")
//            .addResourceLocations("/public", "classpath:/static/")
//            .setCachePeriod(31556926);
  }
}