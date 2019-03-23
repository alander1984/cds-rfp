package tech.lmru.cdsrfp.service;

import io.grpc.stub.StreamObserver;
import tech.lmru.grpc.GRPCService;
import tech.lmru.orders.grpc.service.generated.impl.Empty;
import tech.lmru.orders.grpc.service.generated.impl.Order;
import tech.lmru.orders.grpc.service.generated.impl.OrderList;
import tech.lmru.orders.grpc.service.generated.impl.OrderPosition;
import tech.lmru.orders.grpc.service.generated.impl.PositionList;
import tech.lmru.orders.grpc.service.generated.impl.PositionRequest;
import tech.lmru.orders.grpc.service.generated.impl.OrderServiceGrpc.OrderServiceImplBase;

@GRPCService
public class OrderGRPCService extends OrderServiceImplBase {
    
    @Override
    public void getOrderList(Empty request, StreamObserver<OrderList> responseObserver) {

        Order order1 = Order.newBuilder()
            .setId(0)
            .setOrderNumber("105-ааа-ббб")
            .setDeliveryPrognosis("15:43")
            .setDeliveryWindowStart("12:00")
            .setDeliveryWindowEnd("18:00")
            .setCustomerName("Рулон Обоев")
            .setCustomerPhone("+71231231415")
            .setCustomerAddress("Самарская д 1 кв 1")
            .setCustomerComment("Стороожу сказать Леруа")
            .setWeight(3)
            .addService("до крвартиры")
            .addService("сборка")
            .setStatus("active")
            .setLat(53.180177f)
            .setLon(50.097157f)
            .build();
            
        Order order2 = Order.newBuilder()
            .setId(1)
            .setOrderNumber("104-ааа-ббб")
            .setDeliveryPrognosis("16:43")
            .setDeliveryWindowStart("12:00")
            .setDeliveryWindowEnd("18:00")
            .setCustomerName("Рулониус Обоевенко")
            .setCustomerPhone("+79991231415")
            .setCustomerAddress("Самарская д 1 кв 1")
            .setCustomerComment("Пароль пароль")
            .setWeight(3)
            .addService("до подъезда")
            .setStatus("active")
            .setLat(53.183141f)
            .setLon(50.098898f)
            .build();
            
        OrderList response = OrderList.newBuilder()
                    .addOrders(order1)
                    .addOrders(order2)
                    .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
public void getPositionList(PositionRequest request, StreamObserver<PositionList> responseObserver) {
        OrderPosition position1 = OrderPosition.newBuilder()
            .setId(0)
            .setName("Ламинат")
            .setVendorCode("Л29546873294875")
            .setQuantity(10)
            .build();
        OrderPosition position2 = OrderPosition.newBuilder()
            .setId(1)
            .setName("Плитка")
            .setVendorCode("П29546873294875")
            .setQuantity(4)
            .build();
            
        OrderPosition position3 = OrderPosition.newBuilder()
            .setId(2)
            .setName("Гипсокартон")
            .setVendorCode("Г29546873294875")
            .setQuantity(2)
            .build();
        PositionList response = PositionList.newBuilder()
            .addPositions(position1)
                        .addPositions(position2)
                                    .addPositions(position3)
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
}
    
}
