package tech.lmru.yandex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tech.lmru.cdsrfp.config.ApplicationProperties;
import tech.lmru.integration.LoggingRequestInterceptor;
import tech.lmru.yandex.dto.OptimizationTask;
import tech.lmru.yandex.dto.QueuedTask;

@Service
public class RouteOptimizer {
    
    @Autowired
    ApplicationProperties applicationProperties;
    
    public QueuedTask startOptimization(OptimizationTask optimizationTask) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        String yandexUrl = applicationProperties.getYandex().getUrl();
        ResponseEntity<QueuedTask> queuedTask = restTemplate.postForEntity(yandexUrl+"/add/mvrp", optimizationTask, QueuedTask.class);
        if (queuedTask.getStatusCode()==HttpStatus.ACCEPTED) {
            System.out.println("--==success responce recieved==--");            
        }
        return null;          
    }
    
}
