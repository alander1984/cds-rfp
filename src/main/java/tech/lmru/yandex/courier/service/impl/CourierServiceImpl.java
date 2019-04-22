package tech.lmru.yandex.courier.service.impl;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.lmru.repo.DriverRepository;
import tech.lmru.yandex.courier.dto.BatchResponseDto;
import tech.lmru.yandex.courier.dto.CourierDto;
import tech.lmru.yandex.courier.service.CourierService;
import tech.lmru.yandex.courier.util.MobilePhoneCreator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ilya on 22.04.2019.
 */
@Service
public class CourierServiceImpl implements CourierService {

    private Logger logger = LoggerFactory.getLogger(CourierServiceImpl.class);

    private final DriverRepository driverRepository;
    private final MobilePhoneCreator phoneCreator;
    private final YandexCourierImpl yandexCourier;

    public CourierServiceImpl(DriverRepository driverRepository, MobilePhoneCreator phoneCreator, YandexCourierImpl yandexCourier) {
        this.driverRepository = driverRepository;
        this.phoneCreator = phoneCreator;
        this.yandexCourier = yandexCourier;
    }

    @Override
    public void updateAll() {
        logger.info("Start update couries");
        List<CourierDto> couriers = driverRepository.findAll().stream()
                .map(dr -> CourierDto.builder()
                    .name(String.join(" ", dr.getName(), dr.getSurname()))
                    .phone(phoneCreator.createMobilePhone(null))//TODO
                    .number(dr.getLogin())//TODO or getId?
                    .smsEnabled(true)
                    .build()
                ).collect(Collectors.toList());
        BatchResponseDto response = yandexCourier.updateCouriers(couriers);
        logger.info("result {}", response);
    }
}
