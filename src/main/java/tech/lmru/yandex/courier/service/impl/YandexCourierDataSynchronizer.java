package tech.lmru.yandex.courier.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tech.lmru.yandex.courier.service.CourierService;
import tech.lmru.yandex.courier.service.DepotService;

import javax.annotation.PostConstruct;

/**
 * Created by Ilya on 21.04.2019.
 */
@Component
@Profile("local")
public class YandexCourierDataSynchronizer {

    private Logger logger = LoggerFactory.getLogger(YandexCourierDataSynchronizer.class);

    private final DepotService depotService;
    private final CourierService courierService;

    @Autowired
    public YandexCourierDataSynchronizer(DepotService depotService, CourierService courierService) {
        this.depotService = depotService;
        this.courierService = courierService;
    }

    @PostConstruct
    public void init(){
        synchronizeAll();
    }

    public void synchronizeAll(){
        logger.info("Synchronization all data Yandex Courier");
        depotService.updateAll();
        courierService.updateAll();
        logger.info("Synchronization finished");
    }
}
