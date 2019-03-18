package tech.lmru.grpc;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import tech.lmru.cdsrfp.config.ApplicationProperties;
import tech.lmru.cdsrfp.security.CDSSecurity;
import tech.lmru.cdsrfp.service.BeanUtils;


public class AuthInterceptor implements ServerInterceptor {
    
  public static final Context.Key<Object> USER_IDENTITY
      = Context.key("identity"); // "identity" is just for debugging

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call,
      Metadata headers,
      ServerCallHandler<ReqT, RespT> next) {
    // You need to implement validateIdentity
    boolean identity = validateIdentity(headers);
    System.out.println(identity);
    if (!identity ) { // this is optional, depending on your needs
      // Assume user not authenticated
      call.close(Status.UNAUTHENTICATED.withDescription("some more info"),
                 new Metadata());
      return new ServerCall.Listener() {};
    }
    Context context = Context.current().withValue(USER_IDENTITY, identity);
    return Contexts.interceptCall(context, call, headers, next);
  }
  
  //**
  //   Должно быть переопределено в конкретном микросервисе. Тут бизнес-логика определения прав     
  //
  private boolean validateIdentity(Metadata headers){
        ApplicationProperties properties = BeanUtils.getBean(ApplicationProperties.class);
        CDSSecurity cdsSecurity = BeanUtils.getBean(CDSSecurity.class);
        Metadata.Key<String> key = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        
        String authHeader = headers.get(key);
        System.out.println("ебучий токен1: "+headers);
        authHeader = authHeader.substring(8);
        System.out.println("ебучий токен2: "+authHeader);
        cdsSecurity.checkTokenIsValid(authHeader);       
       return properties.getEnableGrpcSecurity()?cdsSecurity.checkTokenIsValid(authHeader):true;
  }
}