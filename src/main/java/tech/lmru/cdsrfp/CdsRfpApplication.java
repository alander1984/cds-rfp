package tech.lmru.cdsrfp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tech.lmru.cdsrfp.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class })
public class CdsRfpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdsRfpApplication.class, args);
	}

}
