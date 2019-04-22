package tech.lmru.cdsrfp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.lmru.cdsrfp.config.ApplicationProperties;
import tech.lmru.entity.deliveryzone.DeliveryZone;
import tech.lmru.entity.deliveryzone.DeliveryZoneCoordinate;
import tech.lmru.repo.DeliveryZoneCoordinateRepository;
import tech.lmru.repo.DeliveryZoneRepository;

import java.util.Arrays;
import java.util.List;

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
