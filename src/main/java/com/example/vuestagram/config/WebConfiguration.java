package com.example.vuestagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // RestController 의 모든 URL 에 "/api" Prefix 를 설정
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class)); // 진입하는 path 를 어떻게 할 것인가 조절
        // (추가할 prefix, 요청할 Handler(여긴 거의 고정))
    }
}
