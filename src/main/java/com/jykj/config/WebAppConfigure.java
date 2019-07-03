package com.jykj.config;

import com.jykj.interceptor.WebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigure extends WebMvcConfigurerAdapter {

    private final String path = "/service/";

    @Bean   //把我们的拦截器注入为bean
    public HandlerInterceptor getWebInterceptor() {
        return new WebInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//         addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
//         excludePathPatterns 用户排除拦截
        registry.addInterceptor(getWebInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/swagger-resources/configuration/ui", "/swagger-ui.html/**", "/v2/**", "/webjars/**",
                        path + "login", path + "logout", path + "test/test", path + "model/**", path + "editor/stencilset", path + "proManager/download");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**", "swagger-ui.html").
                addResourceLocations("classpath:/static/**", "classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
