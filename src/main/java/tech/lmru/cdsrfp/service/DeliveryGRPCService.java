package tech.lmru.cdsrfp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.lmru.cdsrfp.service.Delivery.Builder;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.DeliveryRepository;

@GRPCService
public class DeliveryGRPCService extends
    tech.lmru.cdsrfp.service.DeliveryServiceGrpc.DeliveryServiceImplBase {
   
   @Inject
   private DeliveryRepository deliveryRepository;
   
   private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:ss.SSS");
   
   private final Logger logger = LoggerFactory.getLogger(DeliveryGRPCService.class);
        
        @Override
        public void readAllDelivery(tech.lmru.cdsrfp.service.Empty request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.service.DeliveryAllResponse> responseObserver) {
            List<tech.lmru.entity.order.Delivery> newDel = deliveryRepository.findAll().stream().filter(d -> (d.getStatus() == tech.lmru.entity.order.DeliveryStatusEnum.NEW)).collect(Collectors.toList());
            logger.info("List size of all deliveries {}", newDel.size());
            DeliveryAllResponse response = null;
            if (newDel.isEmpty()) {
                response = DeliveryAllResponse.newBuilder().build();
            } else {
                List<Delivery> deliveries = newDel.stream().map(delivery ->{
                    //logger.info("List size of all deliveries.items {}", delivery.getItems().size());
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
                        logger.info("Users, Store builded");
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
                            .setFullName(delivery.getFullName())
                            .setExternal(delivery.getExternal())
                            .setZone(delivery.getZone())
                            .setMetroStation(delivery.getMetroStation())
                            .setFloor(delivery.getFloor())
                            .setCompany(delivery.getCompany())
                            .setPhone(delivery.getPhone())
                            .setPhoneSecondary(delivery.getPhoneSecondary())
                            .setEmail(delivery.getEmail())
                            .setUnloadType(delivery.getUnloadType())
                            .setPaper(delivery.getPaper())
                            .setPaymentStatus(delivery.getPaymentStatus())
                            .setConsignee(delivery.getConsignee())
                            .setStore(s);
                            
                            return build.build();
            
                }).collect(Collectors.toList());
                response = DeliveryAllResponse.newBuilder()
                    .addAllDeliveries(deliveries).build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        }
    
        @Override
        public void readAllDeliveryItemForDeliveryById(tech.lmru.cdsrfp.service.DeliveryIdRequest request,
            io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.service.DeliveryItemAllResponse> responseObserver) {
            Long id = request.getId();
            Optional<tech.lmru.entity.order.Delivery> byId = deliveryRepository.findById(id);
            DeliveryItemAllResponse response = null;
            if (byId.isPresent()) {
              tech.lmru.entity.order.Delivery delivery = byId.get();
              logger.info("Items Size: " + delivery.getItems().size());
              List<tech.lmru.cdsrfp.service.DeliveryItem> di = delivery.getItems().stream().map(item ->{
                                //logger.info("Items building");
                                    return tech.lmru.cdsrfp.service.DeliveryItem.newBuilder()
                                        .setId(item.getId())
                                        .setProductLMCode(item.getProductLMCode())
                                        .setProductLMName(item.getProductLMName())
                                        .setWeight(item.getWeight().doubleValue())
                                        .setHeight(item.getHeight().doubleValue())
                                        .setWidth(item.getWidth().doubleValue())
                                        .setLength(item.getLength().doubleValue())
                                        .setQuantity(item.getQuantity().doubleValue())
                                        .setLoadedQuantity(item.getLoadedQuantity().doubleValue())
                                        .setApprovedQuantity(item.getApprovedQuantity().doubleValue())
                                        //.setStatus(st)
                                        .build();
                            }).collect(Collectors.toList());
             response =  DeliveryItemAllResponse.newBuilder().addAllItems(di).build();
             responseObserver.onNext(response);
             responseObserver.onCompleted();
            }else {
                responseObserver.onCompleted();
                throw new EntityNotFoundException();
            }
            
            
        }

}