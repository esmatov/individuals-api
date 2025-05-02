package net.esmatov.individuals_api;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import net.esmatov.individuals_api.configuration.KeycloakConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.List;

@TestConfiguration(proxyBeanMethods = false)
public class TestEnvironmentConfiguration {

    static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:25.0";
    static String realmImportFile = "keycloak/test-realm-export-microservices.json";

    @Bean
    WebClient keycloakTestClient(WebClient.Builder wcBuilder, KeycloakConfigurationProperties kcProperties) {
        return wcBuilder
                .baseUrl(kcProperties.getServer() + ":" + kcProperties.getPort())
                .build();
    }

    @Bean
    KeycloakContainer keycloak(KeycloakConfigurationProperties kcProperties) {
        KeycloakContainer keycloak = new KeycloakContainer(KEYCLOAK_IMAGE)
                .withRealmImportFile(realmImportFile);
        keycloak.setPortBindings(List.of(kcProperties.getPort() + ":8080"));
        keycloak.waitingFor(Wait.forHttp("/realms/master"));
        return keycloak;
    }

}
