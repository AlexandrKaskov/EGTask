package com.example.egtask.configuration;

import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class WebApplicationConfiguration implements WebMvcConfigurer {

    @Value("${server.cors.enable:false}")
    private boolean enableCors;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("CORS enabled: " + enableCors);
        if (enableCors) {
            registry.addMapping("/**").allowedMethods("POST", "PATCH", "DELETE", "GET");
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
    }
}
