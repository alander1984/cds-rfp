package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.VehicleRepository;
import tech.lmru.entity.transport.Driver;
import tech.lmru.entity.transport.TransportCompany;
import tech.lmru.cdsrfp.service.Vehicle.Builder;

@GRPCService
public class VehicleGRPCService extends
    tech.lmru.cdsrfp.service.VehicleServiceGrpc.VehicleServiceImplBase {

  private VehicleRepository repository;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    if(!request.getDriversList().isEmpty()){
              Set<Driver> collect = request.getDriversList().stream()
              .map(driver -> {
                        Driver d = new Driver();
                        d.setId(driver.getId());
                        d.setSurname(driver.getSurname());
                        d.setName(driver.getName());
                        d.setPatronymic(driver.getPatronymic());
                        //d.setBirthday(LocalDateTime.parse(driver.getBirthday(), formatter));
                        d.setLogin(driver.getLogin());
                        d.setPassword(driver.getPassword());
                        return d;
              }).collect(Collectors.toSet());

          vehicle.setDrivers(collect);
        }
    
    if(!request.getTransportCompaniesList().isEmpty()){
            Set<TransportCompany> collect1 = request.getTransportCompaniesList().stream()
            .map(tc -> {
                    TransportCompany t = new TransportCompany();
                    t.setId(tc.getId());
                    t.setCode(tc.getCode());
                    t.setName(tc.getName());
                    return t;
            }).collect(Collectors.toSet());
        vehicle.setTransportCompanies(collect1);
    }
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
      List<Vehicle> collect = all.stream().map(vehicle -> {
          Builder build = Vehicle.newBuilder()
          .setId(vehicle.getId())
          .setCapacity(vehicle.getCapacity())
          .setModel(vehicle.getModel())
          .setRegistrationNumber(vehicle.getRegistrationNumber())
          .setTonnage(vehicle.getTonnage());
          //.build()).collect(Collectors.toList());
          if(!vehicle.getDrivers().isEmpty()){
              List<tech.lmru.cdsrfp.service.Driver> collect1 = vehicle.getDrivers().stream()
              .map(driver ->
                  tech.lmru.cdsrfp.service.Driver.newBuilder()
                        .setId(driver.getId())
                        .setSurname(driver.getSurname())
                        .setName(driver.getName())
                        .setPatronymic(driver.getPatronymic())
                        .setBirthday(driver.getBirthday().toString())
                        .setLogin(driver.getLogin())
                        .setPassword(driver.getPassword())
                        .build()
              ).collect(Collectors.toList());

          build.addAllDrivers(collect1);
        } else {
          build.addAllDrivers(Collections.EMPTY_LIST);
        }
        
        if(!vehicle.getTransportCompanies().isEmpty()){
            List<tech.lmru.cdsrfp.service.TransportCompany> collect2 = vehicle.getTransportCompanies().stream()
            .map(tc ->
                tech.lmru.cdsrfp.service.TransportCompany.newBuilder()
                        .setId(tc.getId())
                        .setCode(tc.getCode())
                        .setName(tc.getName())
                        .build()
            ).collect(Collectors.toList());
            build.addAllTransportCompanies(collect2);
        } else {
          build.addAllTransportCompanies(Collections.EMPTY_LIST);
        }

          return build.build();
      }).collect(Collectors.toList());
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