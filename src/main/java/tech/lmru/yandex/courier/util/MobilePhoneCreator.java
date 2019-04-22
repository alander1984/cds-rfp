package tech.lmru.yandex.courier.util;

import org.springframework.stereotype.Component;

/**
 * Created by Ilya on 22.04.2019.
 */
@Component
public class MobilePhoneCreator {

    public String createMobilePhone(String number){
        if(number == null || number.length() < 10){
            return null;
        }
        return "+7"+number.substring(number.length() - 10, number.length());
    }
}
