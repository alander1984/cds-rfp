package tech.lmru.cdsrfp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import org.springframework.transaction.annotation.Transactional;
import tech.lmru.cdsrfp.service.Route.Builder;
import tech.lmru.entity.plan.OptimizationTask;
import tech.lmru.entity.transport.Vehicle;
import tech.lmru.grpc.GRPCService;
import tech.lmru.repo.DeliveryRepository;
import tech.lmru.repo.RouteRepository;
import tech.lmru.yandex.service.RouteOptimizer;

@GRPCService
public class RouteGRPCService extends RouteServiceGrpc.RouteServiceImplBase {

  private Logger logger = LoggerFactory.getLogger(RouteGRPCService.class);

  private RouteOptimizer routeOptimizer;
  private RouteRepository repository;
  private DeliveryRepository deliveryRepository;
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Autowired
  public RouteGRPCService(RouteOptimizer routeOptimizer, RouteRepository repository, DeliveryRepository deliveryRepository) {
    this.routeOptimizer = routeOptimizer;
    this.repository = repository;
    this.deliveryRepository = deliveryRepository;
  }

  
  @Transactional
  @Override
  public void createOrUpdateRoute(Route request,
      StreamObserver<EntityCreateResponse> responseObserver) {
    tech.lmru.entity.route.Route route = new tech.lmru.entity.route.Route();

    tech.lmru.entity.route.Route routeExist = null;
    List<tech.lmru.entity.route.RoutePoint> routerPoints = null;

    if (request.getId() != 0){
      Optional<tech.lmru.entity.route.Route> byId = repository.findById(request.getId());
      if (byId.isPresent()){
        routeExist = byId.get();
        routerPoints = routeExist.getRouterPoints();
      }
    }

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

    if (request.hasStore()) {
      Store requestStore = request.getStore();
      tech.lmru.entity.store.Store store = new tech.lmru.entity.store.Store();
      store.setId(requestStore.getId());
      route.setStore(store);
    }

    //add Driver

    if (request.hasDriver()) {
      tech.lmru.entity.transport.Driver driver = new tech.lmru.entity.transport.Driver();
      driver.setId(request.getDriver().getId());
      route.setDriver(driver);
    }

    //add RoutePoints
    //Only create new RoutePoint with existing Route
    if (!request.getRouterPointsList().isEmpty()){
      List<tech.lmru.entity.route.RoutePoint> collect = request.getRouterPointsList().stream()
          .map(routePoint -> {
            tech.lmru.entity.route.RoutePoint point = new tech.lmru.entity.route.RoutePoint();
            point.setPos(routePoint.getPos());
            point.setArrivalTime(BigDecimal.valueOf(routePoint.getArrivalTime()));

            tech.lmru.entity.order.Delivery delivery = new tech.lmru.entity.order.Delivery();
            delivery.setId(routePoint.getDelivery().getId());
            point.setDelivery(delivery);
            return point;
          }).collect(Collectors.toList());

      if (routerPoints != null && !routerPoints.isEmpty()) {

//          for(tech.lmru.entity.route.RoutePoint rp : routerPoints) {
//              boolean isExist = false;
//              for(tech.lmru.entity.route.RoutePoint rp1 : collect) {
//                  if(rp1.getId() == rp.getId()) {
//                      isExist = true;
//                  }
//              }
//              
//              if(!isExist) {
//                  tech.lmru.entity.order.Delivery delivery = rp.getDelivery();
//                  delivery.setStatus(tech.lmru.entity.order.DeliveryStatusEnum.NEW);
//                  deliveryRepository.save(delivery);
//              }
//          }
        collect.addAll(routerPoints);
      }

      route.setRouterPoints(collect);

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
          logger.info("####ROUTE WITH ID {}", route.getId());
        Builder builder = Route.newBuilder()
            .setId(route.getId())
            .setName(route.getName())
            .setDeliveryDate(route.getDeliveryDate().format(formatter))
            .setStore(Store.newBuilder()
                .setId(route.getStore().getId())
                .setName(route.getStore().getName())
                .setLon(route.getStore().getLon().doubleValue())
                .setLat(route.getStore().getLat().doubleValue())
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

        if (route.getDriver() != null) {
          builder.setDriver(Driver.newBuilder()
              .setId(route.getDriver().getId())
              .setName(route.getDriver().getName())
              .setSurname(route.getDriver().getSurname())
              .setPatronymic(route.getDriver().getPatronymic())
          );
        }
        if(!route.getRouterPoints().isEmpty()) {
            logger.info("#######SET ROUTER POINTS");
            tech.lmru.cdsrfp.service.RoutePoint.Builder routePointsBuilder = tech.lmru.cdsrfp.service.RoutePoint.newBuilder();
            List<tech.lmru.cdsrfp.service.RoutePoint> serviceRoutePoints = new ArrayList<tech.lmru.cdsrfp.service.RoutePoint>();
            for(tech.lmru.entity.route.RoutePoint entityRoutePoint : route.getRouterPoints()) {
                logger.info("####ROUTE POINT ID: {}", entityRoutePoint.getId());
                tech.lmru.cdsrfp.service.RoutePoint.Builder routePointBuilder = tech.lmru.cdsrfp.service.RoutePoint.newBuilder();
                tech.lmru.cdsrfp.service.Delivery serviceDelivery = null;
                if(entityRoutePoint.getDelivery() != null) {
                    logger.info("#####SET DELIVERY");
                    tech.lmru.entity.order.Delivery entityDelivery = entityRoutePoint.getDelivery();
                    logger.info("#####DELIVERY LON: {}", entityDelivery.getLon());
                    logger.info("#####DELIVERY LAT: {}", entityDelivery.getLat());
                    serviceDelivery = tech.lmru.cdsrfp.service.Delivery.newBuilder()
                                                                            .setId(entityDelivery.getId())
                                                                            .setCity(entityDelivery.getCity())
                                                                            .setStreet(entityDelivery.getStreet())
                                                                            .setHouse(entityDelivery.getHouse())
                                                                            .setEntrance(entityDelivery.getEntrance())
                                                                            .setHouse(entityDelivery.getHouse())
                                                                            .setLon(entityDelivery.getLon().doubleValue())
                                                                            .setLat(entityDelivery.getLat().doubleValue())
                                                                        .build();                                                      
                }       
                tech.lmru.cdsrfp.service.RoutePoint serviceRoutePoint = routePointBuilder
                                                                            .setId(entityRoutePoint.getId())
                                                                            .setPos(entityRoutePoint.getPos())
                                                                            .setArrivalTime(entityRoutePoint.getArrivalTime().doubleValue())
                                                                            .setDelivery(serviceDelivery)
                                                                        .build();
                logger.info("LON FROM ROUTE POINT: {}", serviceRoutePoint.getDelivery().getLon());
 
                serviceRoutePoints.add(serviceRoutePoint);
            }
            builder.addAllRouterPoints(serviceRoutePoints);

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
  
  
  @Override
  @Transactional
  public void acceptRouteGPSData(RouteTrackPacket request, 
    StreamObserver<RouteTrackResponse> responseObserver) {
        logger.warn("entering acceptRouteGPSData");
        List<tech.lmru.entity.route.RouteTrack> routerTracks = null;
        tech.lmru.entity.route.Route routeExist = null;
        if (!request.getRouteTrackList().isEmpty()){
          List<tech.lmru.entity.route.RouteTrack> collect = request.getRouteTrackList().stream()
              .map(routeTrack -> {
                tech.lmru.entity.route.RouteTrack track = new tech.lmru.entity.route.RouteTrack();
                track.setTimestamp(routeTrack.getTimeStamp());
                track.setLat(routeTrack.getLat());
                track.setLon(routeTrack.getLon());
    
                return track;
              }).collect(Collectors.toList());
    
          Optional<tech.lmru.entity.route.Route> byId = repository.findById(request.getRouteId());
          if (byId.isPresent()){
            routeExist = byId.get();
            routerTracks = routeExist.getRouteTracks();
            if (routerTracks != null && !routerTracks.isEmpty()) {
                collect.addAll(routerTracks);
            }
            routeExist.setRouteTracks(routerTracks);
            try {
                repository.save(routeExist);
                responseObserver.onNext(RouteTrackResponse.newBuilder().setResult(true).build());
            } catch(Exception e) {
                logger.warn(e.getMessage());
                responseObserver.onNext(RouteTrackResponse.newBuilder().setResult(false).build());
            } finally {
                responseObserver.onCompleted();
            } 
          } else {
              logger.warn("route with id = " + request.getRouteId() + "not found");
              responseObserver.onNext(RouteTrackResponse.newBuilder().setResult(false).build());
              responseObserver.onCompleted();
          }

        } else {
            logger.warn("empty route track list in request");
            responseObserver.onNext(RouteTrackResponse.newBuilder().setResult(false).build());
            responseObserver.onCompleted();
        }
    }
}