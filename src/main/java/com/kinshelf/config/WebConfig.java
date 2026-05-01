package com.kinshelf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //évite de devoir mettre @crossOrigin sur tous les controllers
        registry.addMapping("/**") // autorise tous les endpoints
                .allowedOrigins("http://localhost:5173") // depuis localhost 5173
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("*");
    }
}