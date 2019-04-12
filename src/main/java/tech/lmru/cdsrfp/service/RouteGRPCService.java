package tech.lmru.cdsrfp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import tech.lmru.cdsrfp.service.Route.Builder;
import tech.lmru.entity.plan.OptimizationTask;
import tech.lmru.entity.transport.Vehicle;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.RouteRepository;
import tech.lmru.yandex.service.RouteOptimizer;

@GRPCService
public class RouteGRPCService extends RouteServiceGrpc.RouteServiceImplBase {

  private Logger logger = LoggerFactory.getLogger(RouteGRPCService.class);

  private RouteOptimizer routeOptimizer;
  private RouteRepository repository;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Autowired
  public RouteGRPCService(RouteOptimizer routeOptimizer, RouteRepository repository) {
    this.routeOptimizer = routeOptimizer;
    this.repository = repository;
  }

  @Override
  public void createOrUpdateRoute(Route request,
      StreamObserver<EntityCreateResponse> responseObserver) {
    tech.lmru.entity.route.Route route = new tech.lmru.entity.route.Route();
    route.setId(request.getId());
    route.setName(request.getName());
    LocalDate ld = LocalDate.parse(request.getDeliveryDate(), formatter);
    LocalDateTime deliveryDate = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    route.setDeliveryDate(deliveryDate);

    //add Vehicle
    if (request.hasVehicle()) {
      tech.lmru.cdsrfp.service.Vehicle requestVehicle = request.getVehicle();
      Vehicle vehicleEntity = new Vehicle();
      vehicleEntity.setId(requestVehicle.getId());
      route.setVehicle(vehicleEntity);

    }
    //add TransportCompany
    if (request.hasTransportCompany()) {
      TransportCompany requestTC = request.getTransportCompany();
      tech.lmru.entity.transport.TransportCompany companyEntity = new tech.lmru.entity.transport.TransportCompany();
      companyEntity.setId(requestTC.getId());
      route.setTransportCompany(companyEntity);
    }

    //add Optimization task
    if (request.hasOptimizationTask()) {
      tech.lmru.cdsrfp.service.OptimizationTask requestOptTask = request.getOptimizationTask();
      OptimizationTask entityOptTask = new OptimizationTask();
      route.setOptimizationTask(entityOptTask);
    }

    if (request.hasStore()){
      Store requestStore = request.getStore();
      tech.lmru.entity.store.Store store = new tech.lmru.entity.store.Store();
      store.setId(requestStore.getId());
      route.setStore(store);
    }

    EntityCreateResponse response = null;
    try {
      tech.lmru.entity.route.Route save = repository.save(route);

      response = EntityCreateResponse.newBuilder()
          .setId(save.getId())
          .build();
      responseObserver.onNext(response);
    } catch (Exception e) {
      logger.warn(e.getMessage());
      response = EntityCreateResponse.newBuilder()
          .build();
      responseObserver.onNext(response);
    } finally {
      responseObserver.onCompleted();
    }
  }

  @Override
  public void readByIdRoute(EntityIdRequest request, StreamObserver<Route> responseObserver) {
    Long id = request.getId();
    Optional<tech.lmru.entity.route.Route> byId = repository.findById(id);

    if (byId.isPresent()) {
      tech.lmru.entity.route.Route routeEntity = byId.get();
      Builder builder = Route.newBuilder()
          .setId(routeEntity.getId())
          .setName(routeEntity.getName())
          .setDeliveryDate(routeEntity.getDeliveryDate().toString());
      Vehicle vehicle = routeEntity.getVehicle();
      if (vehicle != null) {
        builder.setVehicle(tech.lmru.cdsrfp.service.Vehicle.newBuilder()
            .setId(vehicle.getId())
            .setRegistrationNumber(vehicle.getRegistrationNumber())
            .setModel(vehicle.getModel())
            .setTonnage(vehicle.getTonnage())
            .setCapacity(vehicle.getCapacity()).build());
      }

      tech.lmru.entity.transport.TransportCompany transportCompany = routeEntity
          .getTransportCompany();
      if (transportCompany != null) {
        builder.setTransportCompany(TransportCompany.newBuilder()
            .setId(transportCompany.getId())
            .setName(transportCompany.getName())
            .setCode(transportCompany.getCode()).build());
      }

      OptimizationTask optimizationTask = routeEntity.getOptimizationTask();

      if (optimizationTask != null) {
        //TODO set
      }

      Route response = builder.build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } else {
      responseObserver.onCompleted();
      throw new EntityNotFoundException();
    }

  }

  @Override
  public void readAllRoutes(Empty request, StreamObserver<RouteAllResponse> responseObserver) {
    List<tech.lmru.entity.route.Route> all = repository.findAll();
    RouteAllResponse response = null;

    if (!all.isEmpty()) {
      List<Route> collect = all.stream().map(route -> {
        Builder builder = Route.newBuilder()
            .setId(route.getId())
            .setName(route.getName())
            .setDeliveryDate(route.getDeliveryDate().format(formatter))
            .setStore(Store.newBuilder()
                .setId(route.getStore().getId())
                .setName(route.getStore().getName())
                .build());
        if (route.getTransportCompany() != null) {
          builder.setTransportCompany(
              TransportCompany.newBuilder()
                  .setId(route.getTransportCompany().getId())
                  .setCode(route.getTransportCompany().getCode())
                  .setName(route.getTransportCompany().getName())
                  .build()
          );
        }
        if (route.getVehicle() != null) {
          builder.setVehicle(tech.lmru.cdsrfp.service.Vehicle.newBuilder()
                  .setId(route.getVehicle().getId())
                  .setCapacity(route.getVehicle().getCapacity())
                  .setRegistrationNumber(route.getVehicle().getRegistrationNumber())
                  .setModel(route.getVehicle().getModel())
                  .setTonnage(route.getVehicle().getTonnage())
              .addAllDrivers(route.getVehicle().getDrivers().stream().map(driver ->
                  Driver.newBuilder()
                      .setId(driver.getId())
                      .setName(driver.getName())
                      .setSurname(driver.getSurname())
                      .setPatronymic(driver.getPatronymic())
                      .build()).collect(Collectors.toList()))
                  .build()
          );
        }
        return builder.build();
      })
          .collect(Collectors.toList());

      response = RouteAllResponse.newBuilder()
          .addAllRoutes(collect).build();
    } else {
      response = RouteAllResponse.newBuilder().build();
    }

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void deleteByIdRoute(EntityIdRequest request,
      StreamObserver<EntityDeleteResponse> responseObserver) {
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