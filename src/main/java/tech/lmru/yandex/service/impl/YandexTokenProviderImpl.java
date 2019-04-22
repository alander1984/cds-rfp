package tech.lmru.yandex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.lmru.cdsrfp.config.ApplicationProperties;
import tech.lmru.yandex.service.YandexTokenProvider;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya on 21.04.2019.
 */
@Service
public class YandexTokenProviderImpl implements YandexTokenProvider {

    private final RestTemplate restTemplate;
    private final ApplicationProperties appProperties;
    private final Map<String, String> tokens = new HashMap<>();

    @Autowired
    public YandexTokenProviderImpl(RestTemplate restTemplate, ApplicationProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    @PostConstruct
    public void init(){
        tokens.put(appProperties.getYandex().getCourier().getApplId(), appProperties.getYandex().getCourier().getToken());
    }

    @Override
    public String getToken(String applId) {
        return tokens.get(applId);
    }

    @Override
    public void refreshToken(String applId) {
        //TODO ?????
    }
}
