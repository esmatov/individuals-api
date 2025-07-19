package net.smatov.individuals_api.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GlobalRouterConfiguration {

    public static final String REGISTRATION_PATH = "/v1/auth/registration";
    public static final String LOGIN_PATH = "/v1/auth/login";
    public static final String REFRESH_TOKEN_PATH = "/v1/auth/refresh-token";
    public static final String ABOUT_ME = "/v1/auth/me";

    @Bean
    public RouterFunction<ServerResponse> authRoute(GlobalRequestHandler requestHandler) {
        return route(POST(REGISTRATION_PATH).and(contentType(APPLICATION_JSON)),
                requestHandler::handleUserRegistration)

                .andRoute(POST(LOGIN_PATH).and(contentType(APPLICATION_JSON)),
                        requestHandler::handleUserLogin)

                .andRoute(POST(REFRESH_TOKEN_PATH).and(contentType(APPLICATION_JSON)),
                        requestHandler::handleUserRefreshToken)

                .andRoute(GET(ABOUT_ME),
                        requestHandler::handleAboutMe);
    }

}
