package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.VehicleRepository;

@GRPCService
public class VehicleGRPCService extends
    tech.lmru.cdsrfp.service.VehicleServiceGrpc.VehicleServiceImplBase {

  private VehicleRepository repository;

  @Autowired
  public VehicleGRPCService(VehicleRepository repository) {
    this.repository = repository;
  }

  @Transactional
  @Override
  public void createOrUpdateVehicle(tech.lmru.cdsrfp.service.Vehicle request,
      StreamObserver<tech.lmru.cdsrfp.service.EntityCreateResponse> responseObserver) {
    tech.lmru.entity.transport.Vehicle vehicle = new tech.lmru.entity.transport.Vehicle();
    vehicle.setId(request.getId());
    vehicle.setRegistrationNumber(request.getRegistrationNumber());
    vehicle.setModel(request.getModel());
    vehicle.setCapacity(request.getCapacity());
    vehicle.setTonnage(request.getTonnage());


    tech.lmru.entity.transport.Vehicle save = repository.save(vehicle);
    EntityCreateResponse response = EntityCreateResponse.newBuilder().setId(save.getId()).build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();


  }

  @Override
  public void readByIdVehicle(tech.lmru.cdsrfp.service.EntityIdRequest request,
      StreamObserver<tech.lmru.cdsrfp.service.Vehicle> responseObserver) {
    Long id = request.getId();
    Optional<tech.lmru.entity.transport.Vehicle> byId = repository.findById(id);

    if (byId.isPresent()) {
      tech.lmru.entity.transport.Vehicle vehicleEntity = byId.get();
      Vehicle vehicle = Vehicle.newBuilder()
          .setId(vehicleEntity.getId())
          .setCapacity(vehicleEntity.getCapacity())
          .setModel(vehicleEntity.getModel())
          .setRegistrationNumber(vehicleEntity.getRegistrationNumber())
          .build();

      responseObserver.onNext(vehicle);
      responseObserver.onCompleted();
    } else {
      responseObserver.onCompleted();
      throw new EntityNotFoundException();
    }

  }

  @Override
  public void readAllVehicles(tech.lmru.cdsrfp.service.Empty request,
      StreamObserver<tech.lmru.cdsrfp.service.VehicleAllResponse> responseObserver) {
    List<tech.lmru.entity.transport.Vehicle> all = repository.findAll();
    VehicleAllResponse  response = null;

    if (all.isEmpty()){
      response = VehicleAllResponse.newBuilder().build();
    } else {
      List<Vehicle> collect = all.stream().map(vehicle -> Vehicle.newBuilder()
          .setId(vehicle.getId())
          .setCapacity(vehicle.getCapacity())
          .setModel(vehicle.getModel())
          .setRegistrationNumber(vehicle.getRegistrationNumber())
          .setTonnage(vehicle.getTonnage())
          .build()).collect(Collectors.toList());

      response = VehicleAllResponse.newBuilder().addAllVehicles(collect).build();
    }

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Transactional
  @Override
  public void deleteByIdVehicle(tech.lmru.cdsrfp.service.EntityIdRequest request,
      StreamObserver<tech.lmru.cdsrfp.service.EntityDeleteResponse> responseObserver) {
    Long id = request.getId();
    EntityDeleteResponse entityDeleteResponse = null;
    try {
      repository.deleteById(id);
      entityDeleteResponse = EntityDeleteResponse.newBuilder().setSuccess(true).build();
    } catch (Exception e) {
      entityDeleteResponse = EntityDeleteResponse.newBuilder().setSuccess(false).build();
    } finally {
      responseObserver.onNext(entityDeleteResponse);
      responseObserver.onCompleted();
    }
  }
}