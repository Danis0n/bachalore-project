package ru.fp.participantservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.fp.participantservice.service.auth.JWTService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JWTService jwtService(@Value("${auth.jwt.key}") String key) {
        return new JWTService(key);
    }
}
