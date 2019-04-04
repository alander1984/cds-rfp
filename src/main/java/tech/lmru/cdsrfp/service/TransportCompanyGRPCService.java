package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.lmru.entity.transport.Vehicle;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.TransportCompanyRepository;

@GRPCService
public class TransportCompanyGRPCService extends
    tech.lmru.cdsrfp.service.TransportCompanyServiceGrpc.TransportCompanyServiceImplBase {

  private TransportCompanyRepository transportCompanyRepository;

  @Autowired
  public TransportCompanyGRPCService(
      TransportCompanyRepository transportCompanyRepository) {
    this.transportCompanyRepository = transportCompanyRepository;
  }

  @Transactional
  @Override
  public void createOrUpdateTransportCompany(tech.lmru.cdsrfp.service.TransportCompany request,
      StreamObserver<tech.lmru.cdsrfp.service.EntityCreateResponse> responseObserver) {
    tech.lmru.entity.transport.TransportCompany transportCompany = new tech.lmru.entity.transport.TransportCompany();
    transportCompany.setId(request.getId());
    transportCompany.setCode(request.getCode());
    transportCompany.setName(request.getName());

    if (!request.getVehiclesList().isEmpty()) {

      List<Vehicle> collect = request.getVehiclesList().stream().map(vehicle -> {
        Vehicle vehicleEntity = new Vehicle();
        vehicleEntity.setId(vehicle.getId());
        vehicleEntity.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleEntity.setModel(vehicle.getModel());
        vehicleEntity.setCapacity(vehicle.getCapacity());
        vehicleEntity.setTonnage(vehicle.getTonnage());
        return vehicleEntity;
      }).collect(Collectors.toList());
      transportCompany.setVehicles(new HashSet<>(collect));
    }

    tech.lmru.entity.transport.TransportCompany save = transportCompanyRepository
        .save(transportCompany);

    EntityCreateResponse entityCreateResponse = EntityCreateResponse
        .newBuilder()
        .setId(save.getId())
        .build();

    responseObserver.onNext(entityCreateResponse);
    responseObserver.onCompleted();

  }

  @Override
  public void readByIdTransportCompany(tech.lmru.cdsrfp.service.EntityIdRequest request,
      StreamObserver<tech.lmru.cdsrfp.service.TransportCompany> responseObserver) {
    Long id = request.getId();

    Optional<tech.lmru.entity.transport.TransportCompany> byId = transportCompanyRepository
        .findById(id);

    if (byId.isPresent()) {
      tech.lmru.entity.transport.TransportCompany tcEntity = byId.get();

      TransportCompany transportCompany = TransportCompany.newBuilder()
          .setId(tcEntity.getId())
          .setCode(tcEntity.getCode())
          .setName(tcEntity.getName())
          .addAllVehicles(tcEntity.getVehicles().stream().map(vehicle -> tech.lmru.cdsrfp.service.Vehicle.newBuilder()
              .setId(vehicle.getId())
              .setRegistrationNumber(vehicle.getRegistrationNumber())
              .setModel(vehicle.getModel())
              .setCapacity(vehicle.getCapacity())
              .setTonnage(vehicle.getTonnage())
              .build()).collect(Collectors.toList()))
          .build();

      responseObserver.onNext(transportCompany);
      responseObserver.onCompleted();

    } else {
      responseObserver.onCompleted();
      throw new EntityNotFoundException();
    }

  }

  @Override
  public void readAllTransportCompanies(tech.lmru.cdsrfp.service.Empty request,
      StreamObserver<tech.lmru.cdsrfp.service.TransportCompanyAllResponse> responseObserver) {
    List<tech.lmru.entity.transport.TransportCompany> all = transportCompanyRepository.findAll();
    TransportCompanyAllResponse response = null;
    if (all.isEmpty()) {
      response = TransportCompanyAllResponse.newBuilder().build();
    } else {
      List<TransportCompany> collect = all.stream()
          .map(transportCompany -> TransportCompany.newBuilder()
              .setId(transportCompany.getId())
              .setCode(transportCompany.getCode())
              .setName(transportCompany.getName())
              .addAllVehicles(transportCompany.getVehicles().stream().map(vehicle -> tech.lmru.cdsrfp.service.Vehicle.newBuilder()
                  .setId(vehicle.getId())
                  .setRegistrationNumber(vehicle.getRegistrationNumber())
                  .setModel(vehicle.getModel())
                  .setCapacity(vehicle.getCapacity())
                  .setTonnage(vehicle.getTonnage())
                  .build()).collect(Collectors.toList()))
              .build()).collect(Collectors.toList());

      response = TransportCompanyAllResponse.newBuilder()
          .addAllTransportCompanies(collect)
          .build();

    }
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Transactional
  @Override
  public void deleteByIdTransportCompany(tech.lmru.cdsrfp.service.EntityIdRequest request,
      StreamObserver<tech.lmru.cdsrfp.service.EntityDeleteResponse> responseObserver) {

    Long id = request.getId();
    EntityDeleteResponse entityDeleteResponse = null;
    try {
      transportCompanyRepository.deleteById(id);
      entityDeleteResponse = EntityDeleteResponse.newBuilder().setSuccess(true).build();
    } catch (Exception e) {
      entityDeleteResponse = EntityDeleteResponse.newBuilder().setSuccess(false).build();
    } finally {
      responseObserver.onNext(entityDeleteResponse);
      responseObserver.onCompleted();
    }


  }
}
