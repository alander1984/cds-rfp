package tech.lmru.yandex.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.lmru.entity.store.Store;
import tech.lmru.repo.StoreRepository;
import tech.lmru.yandex.courier.dto.DepotDto;
import tech.lmru.yandex.courier.service.DepotService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ilya on 13.04.2019.
 */
@Service
public class DepotServiceImpl implements DepotService {

    private final StoreRepository storeRepository;
    private final YandexCourierImpl yandexCourier;

    @Autowired
    public DepotServiceImpl(StoreRepository storeRepository, YandexCourierImpl yandexCourier) {
        this.storeRepository = storeRepository;
        this.yandexCourier = yandexCourier;
    }

    @Override
    public void updateDepot() {
        List<DepotDto> depots = getListStore().stream()
                .map(st -> DepotDto.builder()
                    .number(st.getCode())
                    .name(st.getName())
                    .address(st.getAddress())
                    .description(st.getComment())
                    .lat(st.getLat().doubleValue())
                    .lon(st.getLon().doubleValue())
                    .timeInterval("8:00-20:00")//TODO
                    .build())
                .collect(Collectors.toList());
        yandexCourier.updateDepots(depots);
    }

    private List<Store> getListStore() {
        return storeRepository.findAll();
    }
}
