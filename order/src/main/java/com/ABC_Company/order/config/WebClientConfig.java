package com.ABC_Company.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(){
        return  WebClient.builder().build();
    }

    public WebClient inventortWebClient(){
        return WebClient.builder().baseUrl("http://localhost:8081/api/v1").build();
    }

    public WebClient productWebClient(){
        return WebClient.builder().baseUrl("http://localhost:8082/api/v1").build();
    }
}
