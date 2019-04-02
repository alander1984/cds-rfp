package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.lmru.cdsrfp.service.Driver.Builder;
import tech.lmru.entity.transport.Vehicle;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.DriverRepository;

@GRPCService
public class DriverGRPCService extends
    tech.lmru.cdsrfp.service.DriverServiceGrpc.DriverServiceImplBase {

  private DriverRepository driverRepository;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @Autowired
  public DriverGRPCService(DriverRepository driverRepository) {
    this.driverRepository = driverRepository;
  }

  @Transactional
  @Override
  public void createOrUpdateDriver(tech.lmru.cdsrfp.service.Driver request,
      StreamObserver<tech.lmru.cdsrfp.service.EntityCreateResponse> responseObserver) {
    tech.lmru.entity.transport.Driver driver = new tech.lmru.entity.transport.Driver();
    if (request.getId() != 0) {
      driver.setId(request.getId());
    }
    driver.setName(request.getName());
    driver.setSurname(request.getSurname());
    driver.setPatronymic(request.getPatronymic());
    driver.setLogin(request.getLogin());
    driver.setPassword(request.getPassword());
    driver.setBirthday(LocalDateTime.parse(request.getBirthday(), formatter));

    if (!request.getVehiclesList().isEmpty()) {

      Set<Vehicle> collect = request.getVehiclesList().stream()
          .map(vehicle -> {
            Vehicle vehicleEntity = new Vehicle();
            vehicleEntity.setId(vehicle.getId());
            vehicleEntity.setRegistrationNumber(vehicle.getRegistrationNumber());
            vehicleEntity.setModel(vehicle.getModel());
            vehicleEntity.setCapacity(vehicle.getCapacity());
            vehicleEntity.setTonnage(vehicle.getTonnage());
            return vehicleEntity;
          }).collect(Collectors.toSet());

      driver.setVehicles(collect);

    }

    tech.lmru.entity.transport.Driver save = driverRepository.save(driver);

    EntityCreateResponse response = EntityCreateResponse
        .newBuilder()
        .setId(save.getId())
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();

  }

  @Override
  public void readByIdDriver(tech.lmru.cdsrfp.service.EntityIdRequest request,
      StreamObserver<tech.lmru.cdsrfp.service.Driver> responseObserver) {
    Long id = request.getId();
    if (id == null) {
      throw new IllegalArgumentException();
    }

    Optional<tech.lmru.entity.transport.Driver> byId = driverRepository.findById(id);
    if (byId.isPresent()) {
      tech.lmru.entity.transport.Driver driverEntity = byId.get();
      Builder builder = Driver.newBuilder()
          .setId(driverEntity.getId())
          .setSurname(driverEntity.getSurname())
          .setName(driverEntity.getName())
          .setPatronymic(driverEntity.getPatronymic())
          .setBirthday(String.valueOf(driverEntity.getBirthday()))
          .setLogin(driverEntity.getLogin())
          .setPassword(driverEntity.getPassword());

      if (!driverEntity.getVehicles().isEmpty()) {
        builder.addAllVehicles(driverEntity.getVehicles().stream().map(vehicle ->
            tech.lmru.cdsrfp.service.Vehicle.newBuilder()
                .setId(vehicle.getId())
                .setTonnage(vehicle.getTonnage())
                .setCapacity(vehicle.getCapacity())
                .setModel(vehicle.getModel())
                .setRegistrationNumber(vehicle.getRegistrationNumber())
                .build()
        ).collect(Collectors.toList()));
      }

      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    } else {
      responseObserver.onCompleted();
      throw new EntityNotFoundException();
    }

  }

  @Override
  public void readAllDrivers(tech.lmru.cdsrfp.service.Empty request,
      StreamObserver<tech.lmru.cdsrfp.service.DriverAllResponse> responseObserver) {
    List<tech.lmru.entity.transport.Driver> all = driverRepository.findAll();
    DriverAllResponse response = null;
    if (all.isEmpty()) {
      response = DriverAllResponse.newBuilder().build();
    } else {
      List<Driver> collect1 = all.stream().map(driver -> {
        Builder build = Driver.newBuilder()
            .setId(driver.getId())
            .setSurname(driver.getSurname())
            .setName(driver.getName())
            .setPatronymic(driver.getPatronymic())
            .setBirthday(driver.getBirthday().toString())
            .setLogin(driver.getLogin())
            .setPassword(driver.getPassword());

        if (!driver.getVehicles().isEmpty()) {
          List<tech.lmru.cdsrfp.service.Vehicle> collect = driver.getVehicles().stream()
              .map(vehicle ->
                  tech.lmru.cdsrfp.service.Vehicle.newBuilder()
                      .setId(vehicle.getId())
                      .setTonnage(vehicle.getTonnage())
                      .setCapacity(vehicle.getCapacity())
                      .setModel(vehicle.getModel())
                      .setRegistrationNumber(vehicle.getRegistrationNumber())
                      .build()
              ).collect(Collectors.toList());

          build.addAllVehicles(collect);
        } else {
          build.addAllVehicles(Collections.EMPTY_LIST);
        }

        return build.build();
      }).collect(Collectors.toList());

      response = DriverAllResponse.newBuilder()
          .addAllDrivers(collect1).build();


    }

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Transactional
  @Override
  public void deleteByIdDriver(tech.lmru.cdsrfp.service.EntityIdRequest request,
      StreamObserver<tech.lmru.cdsrfp.service.EntityDeleteResponse> responseObserver) {
    Long id = request.getId();
    EntityDeleteResponse entityDeleteResponse = null;
    try {
      driverRepository.deleteById(id);
      entityDeleteResponse = EntityDeleteResponse.newBuilder().setSuccess(true).build();
    } catch (Exception e) {
      entityDeleteResponse = EntityDeleteResponse.newBuilder().setSuccess(false).build();
    } finally {
      responseObserver.onNext(entityDeleteResponse);
      responseObserver.onCompleted();
    }

  }
}
