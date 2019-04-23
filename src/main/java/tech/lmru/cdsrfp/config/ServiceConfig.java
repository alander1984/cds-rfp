package tech.lmru.cdsrfp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tech.lmru.cdsrfp.config.rest.CustomErrorHandler;

/**
 * Created by Ilya on 21.04.2019.
 */
@Configuration
public class ServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomErrorHandler());
        return restTemplate;
    }
}
