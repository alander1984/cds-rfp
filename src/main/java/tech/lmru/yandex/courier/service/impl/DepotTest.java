package tech.lmru.yandex.courier.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tech.lmru.yandex.courier.service.DepotService;

import javax.annotation.PostConstruct;

/**
 * Created by Ilya on 21.04.2019.
 */
@Component
@Profile("local")
public class DepotTest {

    private Logger logger = LoggerFactory.getLogger(DepotTest.class);

    private final DepotService depotService;

    @Autowired
    public DepotTest(DepotService depotService) {
        this.depotService = depotService;
    }

    @PostConstruct
    public void init(){
        logger.info("start update depot");
        depotService.updateDepot();
    }
}
