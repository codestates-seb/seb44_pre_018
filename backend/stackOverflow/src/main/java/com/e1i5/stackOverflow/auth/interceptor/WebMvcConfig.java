package com.e1i5.stackOverflow.auth.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

    public WebMvcConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**"); // 모든 경로에 interceptor 적용
//                .excludePathPatterns("/")  // 경로 설정 (이 경로는 제외)
//                .excludePathPatterns()
//                .excludePathPatterns()
//                .excludePathPatterns();
    }
}
