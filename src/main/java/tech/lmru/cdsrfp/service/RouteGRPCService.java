package tech.lmru.cdsrfp.service;

import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import tech.lmru.grpc.AuthInterceptor;
import tech.lmru.grpc.GRPCService;
import tech.lmru.yandex.dto.OptimizationTask;
import tech.lmru.yandex.service.RouteOptimizer;

@GRPCService
public class RouteGRPCService extends RouteServiceGrpc.RouteServiceImplBase {
    
    @Autowired 
    private RouteOptimizer routeOptimizer;
    
    @Override
    public void getRoute(RouteRequest request,
        StreamObserver<Route> responseObserver)  {
            
      OptimizationTask optimizationTask = new OptimizationTask();      
        routeOptimizer.startOptimization(optimizationTask);            
            
            
      Object identity = AuthInterceptor.USER_IDENTITY.get();
      System.out.println("Validate object = "+identity);
      Route response = Route.newBuilder()
                .setCode("test responce")
                .setId(777)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
       
    }

}