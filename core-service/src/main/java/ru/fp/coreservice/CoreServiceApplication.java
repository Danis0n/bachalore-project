package ru.fp.coreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.fp.coreservice.configuration.DestinationsConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfiguration.class)
@EnableScheduling
public class CoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApplication.class, args);
	}
}
