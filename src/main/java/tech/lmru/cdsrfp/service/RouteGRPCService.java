package tech.lmru.cdsrfp.service;

import tech.lmru.grpc.GRPCService;
import tech.lmru.cdsrfp.service.*;
import io.grpc.stub.StreamObserver;
import tech.lmru.cdsrfp.service.Routes;

@GRPCService
public class RouteGRPCService extends RouteServiceGrpc.RouteServiceImplBase {
    
    @Override
    public void getRoute(tech.lmru.cdsrfp.service.RouteRequest request,
        io.grpc.stub.StreamObserver<tech.lmru.cdsrfp.service.Route> responseObserver)  {
      Route response = Route.newBuilder()
                .setCode("asdf")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}