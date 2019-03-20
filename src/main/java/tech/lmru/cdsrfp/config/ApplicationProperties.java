package tech.lmru.cdsrfp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cds-rfp", ignoreUnknownFields = true)
public class ApplicationProperties {
    
    private final Grpc grpc = new Grpc();
    private Boolean enableGrpcSecurity = false;
    private Security security = new Security();
    private Yandex yandex = new Yandex();

    public Grpc getGrpc() { return grpc; }

    public Boolean getEnableGrpcSecurity(){
        return enableGrpcSecurity;
    }

    public void setEnableGrpcSecurity(Boolean enableGrpcSecurity){
        this.enableGrpcSecurity=enableGrpcSecurity;
    }



    public static class Grpc {

        private int port = 8030;

        public int getPort() { return port; }

        public void setPort(int port) { this.port = port; }
    }



    public static class Security {
        private String host;
        private int port;
		/**
		 * @return the host
		 */
		public String getHost() {
			return host;
		}
		/**
		 * @param host the host to set
		 */
		public void setHost(String host) {
			this.host = host;
		}
		/**
		 * @return the port
		 */
		public int getPort() {
			return port;
		}
		/**
		 * @param port the port to set
		 */
		public void setPort(int port) {
			this.port = port;
		}

                
    }



	/**
	 * @return the security
	 */
	public Security getSecurity() {
		return security;
	}

	/**
	 * @param security the security to set
	 */
	public void setSecurity(Security security) {
		this.security = security;
	}
    
 
    public class Yandex{
        private String apiKey;
        private String url;
		/**
		 * @return the apiKey
		 */
		public String getApiKey() {
			return apiKey;
		}
		/**
		 * @param apiKey the apiKey to set
		 */
		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}
		/**
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
        
        
    }



	/**
	 * @return the yandex
	 */
	public Yandex getYandex() {
		return yandex;
	}

	/**
	 * @param yandex the yandex to set
	 */
	public void setYandex(Yandex yandex) {
		this.yandex = yandex;
	}

}