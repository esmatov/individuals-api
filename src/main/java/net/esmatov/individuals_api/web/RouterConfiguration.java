package net.esmatov.individuals_api.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

    public static final String REGISTRATION_PATH = "/v1/auth/registration";

    @Bean
    public RouterFunction<ServerResponse> userRegistrationRoute(CommonRequestHandler requestHandler) {
        return route(POST(REGISTRATION_PATH).and(contentType(APPLICATION_JSON)),
                requestHandler::handleUserRegistration);
    }

}
