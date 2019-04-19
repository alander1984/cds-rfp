package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import tech.lmru.entity.deliveryzone.DeliveryZoneCoordinate;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.DeliveryZoneCoordinateRepository;
import tech.lmru.repo.DeliveryZoneRepository;

import javax.inject.Inject;
import java.util.*;

@GRPCService
public class DeliveryZoneGRPCService
        extends tech.lmru.cdsrfp.service.DeliveryZoneServiceGrpc.DeliveryZoneServiceImplBase {

    private final DeliveryZoneRepository deliveryZoneRepository;
    private final DeliveryZoneCoordinateRepository deliveryZoneCoordinateRepository;

    @Inject
    public DeliveryZoneGRPCService(
            DeliveryZoneCoordinateRepository deliveryZoneCoordinateRepository,
            DeliveryZoneRepository deliveryZoneRepository) {
        this.deliveryZoneCoordinateRepository = deliveryZoneCoordinateRepository;
        this.deliveryZoneRepository = deliveryZoneRepository;
    }

    /**
     * Save new delivery zone
     *
     * @param request          delivery zone as proto-class
     * @param responseObserver empty response
     */
    @Override
    public void createDeliveryZone(DeliveryZone request, StreamObserver<Empty> responseObserver) {
//        if (!checkDeliveryZoneToCreate(request)) return;

        tech.lmru.entity.deliveryzone.DeliveryZone dz;
        dz = new tech.lmru.entity.deliveryzone.DeliveryZone();

        for (CoordinateItem item : request.getCoordinateList()) {
            dz.addCoordinate(new DeliveryZoneCoordinate(
                    item.getLon(), item.getLat(), item.getPos()
            ));
        }

        deliveryZoneRepository.save(dz);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    /**
     * Save new coordinates for existing delivery zone
     *
     * @param request          existing delivery zone as proto-class with id
     * @param responseObserver empty response
     */
    @Override
    public void updateDeliveryZone(DeliveryZone request, StreamObserver<Empty> responseObserver) {
//        if (!checkDeliveryZoneToUpdate(request)) return;

        Optional<tech.lmru.entity.deliveryzone.DeliveryZone> res = deliveryZoneRepository.findById(request.getId());
        if (!res.isPresent()) return;
        tech.lmru.entity.deliveryzone.DeliveryZone thatDz = res.get();

        deliveryZoneCoordinateRepository.deleteAll(thatDz.getCoordinateList());

        List<DeliveryZoneCoordinate> newList = new ArrayList<>(request.getCoordinateCount());
        for (CoordinateItem item : request.getCoordinateList()) {
            DeliveryZoneCoordinate c;
            c = new DeliveryZoneCoordinate(item.getLon(), item.getLat(), item.getPos());
            c.setDeliveryZone(thatDz);

            newList.add(c);
        }

        deliveryZoneCoordinateRepository.saveAll(newList);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    /*private boolean checkDeliveryZoneToCreate(DeliveryZone request) {
        return request != null
                && request.getId() == 0L
                && checkCoordinateList(request.getCoordinateList());
    }

    private boolean checkDeliveryZoneToUpdate(DeliveryZone request) {
        return request != null && checkCoordinateList(request.getCoordinateList());
    }

    private boolean checkCoordinateList(List<CoordinateItem> list) {
        if (list == null || list.size() < 3) return false;

        List<tech.lmru.cdsrfp.service.CoordinateItem> itemList;
        itemList = list instanceof ArrayList ? list : new ArrayList<>(list);

        itemList.sort(Comparator.comparingInt(CoordinateItem::getPos));

        final double z = 0d;
        for (int size = itemList.size(), i = size - 1; i >= 0; i--) {
            CoordinateItem that = itemList.get(i);
            if (that.getPos() != i && that.getLon() != z && that.getLat() != z) {
                return false;
            }
        }
        return true;
    }*/

}
