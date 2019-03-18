package tech.lmru.cdsrfp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import liquibase.util.StringUtils;
import tech.lmru.auth.grpc.service.generated.impl.AccessToken;
import tech.lmru.auth.grpc.service.generated.impl.CheckTokenRequest;
import tech.lmru.auth.grpc.service.generated.impl.TokenServiceGrpc;
import tech.lmru.cdsrfp.config.ApplicationProperties;

@Service
public class CDSSecurity {
    
    @Autowired
    private ApplicationProperties applicationProperties;
    
    public boolean checkTokenIsValid(String token) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(applicationProperties.getSecurity().getHost(),
                applicationProperties.getSecurity().getPort())
                .usePlaintext(true)
                .build();
                
                
        TokenServiceGrpc.TokenServiceBlockingStub stub = TokenServiceGrpc.newBlockingStub(channel);
        AccessToken accessToken =  stub.checkToken(CheckTokenRequest.newBuilder().setToken(token).build());        
        channel.shutdown();
        return StringUtils.isEmpty(accessToken.getError().toString());

    }
}
