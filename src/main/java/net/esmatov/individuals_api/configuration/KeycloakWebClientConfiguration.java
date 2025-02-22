package net.esmatov.individuals_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class KeycloakWebClientConfiguration {

    @Bean
    public WebClient keycloakWebClient(WebClient.Builder wcBuilder, KeycloakConfigurationProperties kcProperties) {
        return wcBuilder
                .baseUrl(kcProperties.getServer())
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

}
