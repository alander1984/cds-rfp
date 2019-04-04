package tech.lmru.cdsrfp.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;

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
            
      
    }
}