package net.esmatov.individuals_api;

import net.esmatov.individuals_api.configuration.KeycloakConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakConfigurationProperties.class)
public class IndividualsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndividualsApiApplication.class, args);
    }

}
