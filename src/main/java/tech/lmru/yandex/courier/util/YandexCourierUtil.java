package tech.lmru.yandex.courier.util;

import tech.lmru.entity.route.Route;
import tech.lmru.entity.store.Store;
import tech.lmru.entity.transport.Driver;

/**
 * Created by Ilya on 22.04.2019.
 */
public class YandexCourierUtil {

    public static String getCourierNumber(Driver driver){
        return String.valueOf(driver.getId());
    }

    public static String getDepotNumber(Store store){
        return store.getCode();
    }

    public static String getRouteNumber(Route route){
        return String.valueOf(route.getId());
    }
}
