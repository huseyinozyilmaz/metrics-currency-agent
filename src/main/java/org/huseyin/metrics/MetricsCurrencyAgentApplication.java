package org.huseyin.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude=MetricFilterAutoConfiguration.class) // silence added metrics noise
public class MetricsCurrencyAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsCurrencyAgentApplication.class, args);
    }
}
