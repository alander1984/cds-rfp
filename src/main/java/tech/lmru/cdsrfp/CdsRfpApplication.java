package tech.lmru.cdsrfp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tech.lmru.cdsrfp.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableJpaRepositories(basePackages = "tech.lmru")
@ComponentScan({"tech.lmru.cdsrfp", "tech.lmru.yandex"})
public class CdsRfpApplication {

    public static void main(String[] args) {
        ApplicationContext context;
        context = SpringApplication.run(CdsRfpApplication.class, args);
    }

}
