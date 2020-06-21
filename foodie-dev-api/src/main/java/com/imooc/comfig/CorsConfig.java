package com.imooc.comfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//跨域
@Configuration
public class CorsConfig {

    public CorsConfig() {

    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //添加跨域配置信息
        config.addAllowedOrigin("http://localhost:8080");

        //设置是否发送cokie信息
        config.setAllowCredentials(true);

        //设置运行请求的方式
        config.addAllowedMethod("*");

        //设置允许的header
        config.addAllowedHeader("*");

        //2.为url添加映射的路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        //3.返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }


}
