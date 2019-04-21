package tech.lmru.yandex.service;

/**
 * Created by Ilya on 21.04.2019.
 */
public interface YandexTokenProvider {

    String getToken(String applId);
    void refreshToken(String applId);
}
