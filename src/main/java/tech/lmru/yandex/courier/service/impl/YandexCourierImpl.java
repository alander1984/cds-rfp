package tech.lmru.yandex.courier.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.lmru.cdsrfp.config.ApplicationProperties;
import tech.lmru.yandex.courier.dto.DepotDto;
import tech.lmru.yandex.courier.dto.ResponseBatchDepotDto;
import tech.lmru.yandex.service.YandexTokenProvider;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Ilya on 21.04.2019.
 */
@Service
public class YandexCourierImpl {

    private static final String URL_DEPOTS_BATH = "/depots-batch";
    private static final int TRY_COUNT = 3;
    private static final int SLEEP_BETWEEN_TRY_SEND_MS = 2000;

    private Logger logger = LoggerFactory.getLogger(YandexCourierImpl.class);

    private final RestTemplate restTemplate;
    private final ApplicationProperties appProperties;
    private final YandexTokenProvider yandexTokenProvider;


    @Autowired
    public YandexCourierImpl(RestTemplate restTemplate, ApplicationProperties appProperties, YandexTokenProvider yandexTokenProvider) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
        this.yandexTokenProvider = yandexTokenProvider;
    }

    public void updateDepots(List<DepotDto> depots) {
        HttpEntity<List<DepotDto>> request = createHttpEntity(depots);
        Supplier<ResponseEntity<ResponseBatchDepotDto>> supplier =
                ()-> restTemplate.exchange(getBaseUrl() + URL_DEPOTS_BATH,
                        HttpMethod.POST, request, ResponseBatchDepotDto.class);
        ResponseBatchDepotDto responce = sendRequest(supplier);
    }

    private <T>HttpEntity<T> createHttpEntity(T object) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Auth " + yandexTokenProvider.getToken(getAppId()));
        HttpEntity entity = new HttpEntity(object, headers);
        return entity;
    }

    private String getAppId() {
        return appProperties.getYandex().getCourier().getApplId();
    }

    private String getBaseUrl(){
        return appProperties.getYandex().getCourier().getUrl();
    }

     private <T>T sendRequest(Supplier<ResponseEntity<T>> supplier){
         for (int i = 0; i < TRY_COUNT; i++) {
             ResponseEntity<T> response = supplier.get();
             switch (response.getStatusCode()){
                 case OK :
                     logger.info(String.format("response OK body %s", response.getBody()));
                     return response.getBody();
                 case UNAUTHORIZED :
                     yandexTokenProvider.refreshToken(getAppId());
                     break;
                 case GATEWAY_TIMEOUT :
                     try {
                         Thread.sleep(SLEEP_BETWEEN_TRY_SEND_MS);
                     } catch (InterruptedException ignored) {}
                     break;
                 default:
                     logger.error(String.format("Can't send request. Error: %s responce %s", response.getStatusCode(), response.getBody()));
                     //throw new RuntimeException(String.format("Can't send request. Error: %s responce %s", response.getStatusCode(), response.getBody()));
             }
         }
         return null;
     }
}
