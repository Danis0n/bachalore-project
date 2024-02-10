package ru.fp.billingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.fp.billingservice.config.DestinationsConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfiguration.class)
@EnableScheduling
public class BillingServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

}
