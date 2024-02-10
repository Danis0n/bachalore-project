package ru.fp.billingservice.config;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class TracerConfiguration {
    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public Tracer getTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration
                .SamplerConfiguration.fromEnv()
                .withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfig = Configuration
                .ReporterConfiguration.fromEnv()
                .withLogSpans(true);
        Configuration config = new Configuration(serviceName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);
        return config.getTracer();
    }

}
