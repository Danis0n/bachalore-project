package ru.di.receiptservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter @Setter
@ConfigurationProperties("destinations")
public class DestinationsConfiguration {

    private Map<String, DestinationExchangeInfo> topics;
    private Map<String, DestinationQueuesInfo> queues;

    @Getter @Setter
    static public class DestinationExchangeInfo {
        private String exchange;
        private String routingKey;
    }

    @Getter @Setter
    static public class DestinationQueuesInfo {
        private String queue;
        private String routingKey;
    }

}
