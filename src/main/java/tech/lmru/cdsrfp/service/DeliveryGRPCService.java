package tech.lmru.cdsrfp.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import tech.lmru.cdsrfp.service.Delivery.Builder;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.DeliveryRepository;

@GRPCService
public class DeliveryGRPCService extends
    tech.lmru.cdsrfp.service.DeliveryServiceGrpc.DeliveryServiceImplBase {
   
   @Inject
   private DeliveryRepository deliveryRepository;
   
   private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:ss.SSS");
        
        @Override
        public void readAllDelivery(tech.lmru.cdsrfp.service.Empty request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.service.DeliveryAllResponse> responseObserver) {
            List<tech.lmru.entity.order.Delivery> newDel = deliveryRepository.newDeliveries();
            DeliveryAllResponse response = null;
            if (newDel.isEmpty()) {
                response = DeliveryAllResponse.newBuilder().build();
            } else {
                List<Delivery> deliveries = newDel.stream().map(delivery ->{
                    User u = User.newBuilder()
                    .setId(delivery.getCreateUser().getId())
                    .setName(delivery.getCreateUser().getName()).build();
                    User ul = User.newBuilder()
                    .setId(delivery.getLastUpdateUser().getId())
                    .setName(delivery.getLastUpdateUser().getName()).build();
                    Store s =  Store.newBuilder()
                    .setId(delivery.getStore().getId())
                    //.setType(s.getType())
                    .setName(delivery.getStore().getName())
                    .setAddress(delivery.getStore().getAddress())
                    .setCode(delivery.getStore().getCode())
                    .setLon(delivery.getStore().getLon().doubleValue())
                    .setLat(delivery.getStore().getLat().doubleValue())
                    .setComment(delivery.getStore().getComment()).build();
                    //DeliveryStatusEnum st;
                    Builder build = Delivery.newBuilder()
                        .setId(delivery.getId())
                        .setLat(delivery.getLat().doubleValue())
                        .setLon(delivery.getLon().doubleValue())
                        .setTimeWindow(delivery.getTimeWindow())
                        .setCreateUser(u)
                        .setCreatedDate(delivery.getCreatedDate().toString())
                        .setLastUpdateUser(ul)
                        .setLastUpdateDate(delivery.getLastUpdateDate().toString())
                        //.setStatus(st)
                        .setDeliveryDateMin(delivery.getDeliveryDateMin().toString())
                        .setDeliveryDateMax(delivery.getDeliveryDateMax().toString())
                        .setComment(delivery.getComment())
                        .setCity(delivery.getCity())
                        .setStreet(delivery.getStreet())
                        .setHouse(delivery.getHouse())
                        .setEntrance(delivery.getEntrance())
                        .setFlat(delivery.getEntrance())
                        .setStore(s)
                        .addAllItems(delivery.getItems().stream().map(item ->{
                                return tech.lmru.cdsrfp.service.DeliveryItem.newBuilder()
                                    .setId(item.getId())
                                    .setProductLMCode(item.getProductLMCode())
                                    .setProductLMName(item.getProductLMName())
                                    .setWeight(item.getWeight().doubleValue())
                                    .setVolume(item.getVolume().doubleValue())
                                    .setWidth(item.getWidth().doubleValue())
                                    .setLength(item.getLength().doubleValue())
                                    .setQuantity(item.getQuantity().doubleValue())
                                    .setLoadedQuantity(item.getLoadedQuantity().doubleValue())
                                    .setApprovedQuantity(item.getApprovedQuantity().doubleValue())
                                    //.setStatus(st)
                                    .build();
                        }).collect(Collectors.toList()));  
                        return build.build();
                }).collect(Collectors.toList());
                response = DeliveryAllResponse.newBuilder()
                    .addAllDeliveries(deliveries).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
    }
}