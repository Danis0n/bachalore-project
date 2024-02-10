package ru.fp.participantservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties("destinations")
public class DestinationsConfig {

    private Map<String, DestinationExchangeInfo> topics = new HashMap<>();

    private Map<String, DestinationQueuesInfo> queues = new HashMap<>();


    @Setter
    @Getter
    static public class DestinationExchangeInfo {

        private String exchange;
        private String routingKey;

    }

    @Setter
    @Getter
    static public class DestinationQueuesInfo {

        private String queue;
        private String routingKey;

    }

}
