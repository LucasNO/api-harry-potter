package com.apiharrypotter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableJpaRepositories("com.apiharrypotter.repository")
@EntityScan("com.apiharrypotter.entity")
@ComponentScan( basePackages = {"com.apiharrypotter.service", "com.apiharrypotter.config"
        , "com.apiharrypotter.mapper", "com.apiharrypotter.controller"})
@SpringBootApplication
@EnableCaching
public class ApiHarryPotterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiHarryPotterApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
