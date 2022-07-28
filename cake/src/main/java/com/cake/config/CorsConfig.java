package com.cake.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        //CORS pre-flight requests 처리, CORS 인터셉트
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //서버 응답시 json을 자바스크립트에서 처리할 수 있게  설정
        config.addAllowedOrigin("*"); //모든 ip에 응답 허용
        config.addAllowedHeader("*"); //모든 header에 응답 허용
        config.addAllowedMethod("*"); //모든 httpMethod에 응답 허용
        source.registerCorsConfiguration("/api/**",config);
        //setCorsConfigurations(Map) 의 변형

        return new CorsFilter(source);

    }
}
