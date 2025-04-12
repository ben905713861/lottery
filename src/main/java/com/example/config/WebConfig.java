package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 *
 * @author Ben
 * @since 2023/9/8
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${uiPath:classpath:/frontend/}")
    private String uiPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 将根路径("/")映射到index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(uiPath);
    }
}
