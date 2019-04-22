package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.DeliveryZoneCoordinateRepository;
import tech.lmru.repo.DeliveryZoneRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void createDeliveryZone(tech.lmru.cdsrfp.service.DeliveryZone request, StreamObserver<Empty> responseObserver) {
//        if (!checkDeliveryZoneToCreate(request)) return;

        tech.lmru.entity.deliveryzone.DeliveryZone dz;
        dz = new tech.lmru.entity.deliveryzone.DeliveryZone();

        for (tech.lmru.cdsrfp.service.CoordinateItem item : request.getCoordinateList()) {
            dz.addCoordinate(new tech.lmru.entity.deliveryzone.DeliveryZoneCoordinate(
                    item.getLon(), item.getLat(), item.getPos())
            );
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
    public void updateDeliveryZone(tech.lmru.cdsrfp.service.DeliveryZone request, StreamObserver<Empty> responseObserver) {
        Optional<tech.lmru.entity.deliveryzone.DeliveryZone> res = deliveryZoneRepository.findById(request.getId());
        if (!res.isPresent()) return;
        tech.lmru.entity.deliveryzone.DeliveryZone thatDz = res.get();

        deliveryZoneCoordinateRepository.deleteAll(thatDz.getCoordinateList());

        List<tech.lmru.entity.deliveryzone.DeliveryZoneCoordinate> newList;
        newList = new ArrayList<>(request.getCoordinateCount());
        for (tech.lmru.cdsrfp.service.CoordinateItem item : request.getCoordinateList()) {
            tech.lmru.entity.deliveryzone.DeliveryZoneCoordinate c;
            c = new tech.lmru.entity.deliveryzone.DeliveryZoneCoordinate(
                    item.getLon(), item.getLat(), item.getPos()
            );
            c.setDeliveryZone(thatDz);

            newList.add(c);
        }

        deliveryZoneCoordinateRepository.saveAll(newList);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
