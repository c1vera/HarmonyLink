package com.dongyang.HarmonyLink;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** 싱글톤 유지를 위한, 별도 Bean들의 생성만을 다루는 클래스입니다.
 * ex. Service나 Repository와 다르게, RestTemplate 등 java 또는 Spring 자체에서 제공하는 클래스를 싱글톤으로... */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
