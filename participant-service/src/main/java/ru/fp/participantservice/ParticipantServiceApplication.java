package ru.fp.participantservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.fp.participantservice.config.DestinationsConfig;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(DestinationsConfig.class)
@Slf4j
public class ParticipantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParticipantServiceApplication.class, args);
	}

}
