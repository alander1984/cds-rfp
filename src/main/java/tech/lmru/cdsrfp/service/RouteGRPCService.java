package tech.lmru.cdsrfp.service;

import tech.lmru.grpc.AuthInterceptor;
import tech.lmru.grpc.GRPCService;
import io.grpc.stub.StreamObserver;

@GRPCService
public class RouteGRPCService extends RouteServiceGrpc.RouteServiceImplBase {
    
    @Override
    public void getRoute(RouteRequest request,
        StreamObserver<Route> responseObserver)  {
      Object identity = AuthInterceptor.USER_IDENTITY.get();
      System.out.println("Validate object = "+identity);
      Route response = Route.newBuilder()
                .setCode("Тестовый ответ")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}