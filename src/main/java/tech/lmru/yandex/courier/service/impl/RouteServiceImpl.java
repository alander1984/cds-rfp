package tech.lmru.yandex.courier.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.lmru.entity.route.Route;
import tech.lmru.repo.RouteRepository;
import tech.lmru.yandex.courier.dto.BatchResponseDto;
import tech.lmru.yandex.courier.dto.RouteDto;
import tech.lmru.yandex.courier.service.RouteService;

import java.util.List;
import java.util.stream.Collectors;

import static tech.lmru.yandex.courier.util.YandexCourierUtil.getCourierNumber;
import static tech.lmru.yandex.courier.util.YandexCourierUtil.getDepotNumber;
import static tech.lmru.yandex.courier.util.YandexCourierUtil.getRouteNumber;

/**
 * Created by Ilya on 22.04.2019.
 */
@Service
public class RouteServiceImpl implements RouteService {

    private Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final RouteRepository routeRepository;
    private final YandexCourierImpl yandexCourier;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, YandexCourierImpl yandexCourier) {
        this.routeRepository = routeRepository;
        this.yandexCourier = yandexCourier;
    }

    @Override
    public void updateAll() {
        logger.info("Start update routes");
        List<RouteDto> routeDtos = getActualRoutes().stream().map(r -> RouteDto.builder()
                .carId(r.getVehicle().getId())
                .courierNumber(getCourierNumber(r.getDriver()))
                .date(r.getDeliveryDate().toString())
                .depotNumber(getDepotNumber(r.getStore()))
                .number(getRouteNumber(r))
               // .imei() //TODO
                .build())
                .collect(Collectors.toList());
        BatchResponseDto resp = yandexCourier.updateRoutes(routeDtos);
        logger.info("result {}", resp);
    }

    private List<Route> getActualRoutes(){
        return routeRepository.findAll();
    }
}
