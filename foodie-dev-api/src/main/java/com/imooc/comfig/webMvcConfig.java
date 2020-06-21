package com.imooc.comfig;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//将静态资源 发布到 web上
@Configuration
public class webMvcConfig implements WebMvcConfigurer {


    /**
     * 实现静态资源的映射
     * 添加处理程序以从Web应用程序根目录，类路径和其他目录下的特定位置提供静态资源，例如图像，js和css *文件。
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/META-INF/resources/")  // 映射swagger2
                .addResourceLocations("file:Z:\\ojb\\foodie-dev\\workspaces\\images\\"); //映射本地资源

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();

    }
}
