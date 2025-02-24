package net.esmatov.individuals_api.web;

import net.esmatov.individuals_api.client.keycloak_model.UserAccessToken;
import net.esmatov.individuals_api.client.keycloak_model.UserRepresentation;
import net.esmatov.individuals_api.dto.UserLoginRequest;
import net.esmatov.individuals_api.dto.UserRefreshTokenRequest;
import net.esmatov.individuals_api.dto.UserRegistrationRequest;
import net.esmatov.individuals_api.service.UserAdministrationService;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class CommonRequestHandler {

    private final UserAdministrationService userAdministrationService;

    public CommonRequestHandler(UserAdministrationService userAdministrationService) {
        this.userAdministrationService = userAdministrationService;
    }

    public Mono<ServerResponse> handleUserRegistration(ServerRequest request) {
        return request.bodyToMono(UserRegistrationRequest.class)
                .flatMap(userAdministrationService::signUp)
                .flatMap(tuples -> {
                    UserRepresentation user = tuples.getT1();
                    UserAccessToken userToken = tuples.getT2();
                    return ServerResponse.created(URI.create(user.getId().toString()))
                            .contentType(APPLICATION_JSON)
                            .bodyValue(userToken);
                });
    }

    public Mono<ServerResponse> handleUserLogin(ServerRequest request) {
        return request.bodyToMono(UserLoginRequest.class)
                .flatMap(userAdministrationService::signIn)
                .flatMap(userToken ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(userToken));
    }

    public Mono<ServerResponse> handleUserRefreshToken(ServerRequest request) {
        return request.bodyToMono(UserRefreshTokenRequest.class)
                .flatMap(userAdministrationService::refreshToken)
                .flatMap(userToken ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(userToken));
    }

    public Mono<ServerResponse> handleAboutMe(ServerRequest request) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getPrincipal() instanceof Jwt)
                .flatMap(authentication ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(userAdministrationService.aboutMe((Jwt) authentication.getPrincipal())));
    }

}
