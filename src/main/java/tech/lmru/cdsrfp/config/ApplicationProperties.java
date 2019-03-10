package tech.lmru.cdsrfp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cds-rfp", ignoreUnknownFields = true)
public class ApplicationProperties {
    private final Grpc grpc = new Grpc();

    public Grpc getGrpc() { return grpc; }

    public static class Grpc {

        private int port = 8030;

        public int getPort() { return port; }

        public void setPort(int port) { this.port = port; }
    }

}