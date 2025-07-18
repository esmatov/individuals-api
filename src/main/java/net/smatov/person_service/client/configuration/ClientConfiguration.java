package net.smatov.person_service.client.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ClientConfiguration {

    @Bean(name = "restCountriesClient")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        Duration duration = Duration.ofSeconds(2);
        return builder
                .rootUri("https://restcountries.com/v3.1")
                .setConnectTimeout(duration)
                .setReadTimeout(duration)
                .build();
    }

}
