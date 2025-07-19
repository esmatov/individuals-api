package net.smatov.individuals_api.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(KeycloakConfigurationProperties.class)
public class KeycloakClientConfiguration {

    @Bean
    public WebClient keycloakWebClient(WebClient.Builder wcBuilder, KeycloakConfigurationProperties kcProperties) {
        return wcBuilder
                .baseUrl(kcProperties.getServer() + ":" + kcProperties.getPort())
                .build();
    }

}
