package tech.lmru.cdsrfp.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import tech.lmru.cdsrfp.service.Store.StoreTypeEnum;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.StoreRepository;

@GRPCService
public class StoreGRPCService extends tech.lmru.cdsrfp.storeservice.StoreServiceGrpc.StoreServiceImplBase {
    
    private StoreRepository repository;
    
    @Autowired
    public StoreGRPCService(StoreRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
    @Override
    public void createOrUpdateStore(tech.lmru.cdsrfp.service.Store request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.storeservice.StoreCreateResponse> responseObserver) {
            tech.lmru.entity.store.Store store = new tech.lmru.entity.store.Store();
            
            
            store.setId(request.getId());
            store.setType(tech.lmru.entity.store.StoreTypeEnum.valueOf(request.getTypeValue()));
            store.setName(request.getName());
            store.setCode(request.getCode());
            store.setAddress(request.getAddress());
            store.setLon(BigDecimal.valueOf(request.getLon()));
            store.setLat(BigDecimal.valueOf(request.getLat()));
            store.setComment(request.getComment());
            
            tech.lmru.entity.store.Store save = repository.save(store);
            
            StoreCreateResponse response = StoreCreateResponse.newBuilder().setId((int)save.getId()).build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
    }

    @Override
    public void getStoreById(tech.lmru.cdsrfp.storeservice.StoreIdRequest request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.service.Store> responseObserver) {
            
        Optional<tech.lmru.entity.store.Store> byId = repository.findById(Long.parseLong(request.getId()));
        
        if(byId.isPresent()) {
            tech.lmru.entity.store.Store store = byId.get();
            Store protoStore = Store.newBuilder()
                                    .setId(store.getId())
                                    .setType(StoreTypeEnum.valueOf(store.getType().ordinal())) 
                                    .setName(store.getName())
                                    .setAddress(store.getAddress())
                                    .setCode(store.getCode())
                                    .setLon(store.getLon().doubleValue())
                                    .setLat(store.getLat().doubleValue())
                                    .setComment(store.getComment())
                                    .build();         
            responseObserver.onNext(protoStore);
            responseObserver.onCompleted();
        } else {
            responseObserver.onCompleted();
            throw new EntityNotFoundException();
        }

    }

    @Override
    public void getAllStore(tech.lmru.cdsrfp.service.Empty request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.storeservice.StoreGetAllResponse> responseObserver) {
            
            List<tech.lmru.entity.store.Store> storesEntity = repository.findAll();
            for(tech.lmru.entity.store.Store store1 : storesEntity) {
                System.out.println("TYPE: " + store1.getType());
            }
            List<Store> stores = storesEntity.stream().map(store -> Store.newBuilder()
                                    .setId(store.getId())
                                    .setType(StoreTypeEnum.valueOf(store.getType().ordinal())) 
                                    .setName(store.getName())
                                    .setAddress(store.getAddress())
                                    .setCode(store.getCode())
                                    .setLon(store.getLon().doubleValue())
                                    .setLat(store.getLat().doubleValue())
                                    .setComment(store.getComment())
                                    .build()).collect(Collectors.toList());         
                                    
            StoreGetAllResponse response = StoreGetAllResponse.newBuilder().addAllStores(stores).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();    
            System.out.println("FFFFFFFF");
    }
    
    @Transactional
    @Override
    public void deleteStoreById(tech.lmru.cdsrfp.storeservice.StoreIdRequest request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.storeservice.StoreDeleteResponse> responseObserver) {
            
            Long id = Long.parseLong(request.getId());
            StoreDeleteResponse response = null;
            try {
              repository.deleteById(id);
              response = StoreDeleteResponse.newBuilder().setSuccess(true).build();
            } catch (Exception e) {
              response = StoreDeleteResponse.newBuilder().setSuccess(false).build();
            } finally {
              responseObserver.onNext(response);
              responseObserver.onCompleted();
            }
    }
}
