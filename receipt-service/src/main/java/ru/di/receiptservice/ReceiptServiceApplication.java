package ru.di.receiptservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.di.receiptservice.configuration.DestinationsConfiguration;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfiguration.class)
public class ReceiptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiptServiceApplication.class, args);
	}

}
