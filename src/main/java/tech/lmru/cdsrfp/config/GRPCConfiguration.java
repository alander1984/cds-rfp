package tech.lmru.cdsrfp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.lmru.cdsrfp.config.ApplicationProperties;
import tech.lmru.grpc.GRPCServerRunner;
import tech.lmru.grpc.GRPCService;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@ConditionalOnBean(annotation = GRPCService.class)
public class GRPCConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public GRPCServerRunner grpcServerRunner() {
        return new GRPCServerRunner(applicationProperties.getGrpc().getPort());
    }
}
