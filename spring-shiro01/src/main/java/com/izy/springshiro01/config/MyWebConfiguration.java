package com.izy.springshiro01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zouyu
 * @description
 * @date 2020/5/29
 */
@Configuration
public class MyWebConfiguration implements WebMvcConfigurer {

    /**
     * 自定义国际化
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}