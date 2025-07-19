package net.smatov.individuals_api.web;

import net.smatov.individuals_api.client.keycloak_model.UserAccessToken;
import net.smatov.individuals_api.client.keycloak_model.UserRepresentation;
import net.smatov.individuals_api.dto.AboutMeResponse;
import net.smatov.individuals_api.dto.UserLoginRequest;
import net.smatov.individuals_api.dto.UserRefreshTokenRequest;
import net.smatov.individuals_api.dto.UserRegistrationRequest;
import net.smatov.individuals_api.service.AdministrationService;
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
public class GlobalRequestHandler {

    private final AdministrationService administrationService;
    private final RequestValidator validator;

    public GlobalRequestHandler(AdministrationService administrationService, RequestValidator validator) {
        this.administrationService = administrationService;
        this.validator = validator;
    }

    public Mono<ServerResponse> handleUserRegistration(ServerRequest registrationRequest) {
        return registrationRequest.bodyToMono(UserRegistrationRequest.class)
                .doOnNext(validator::validate)
                .flatMap(administrationService::signUp)
                .flatMap(tuples -> {
                    UserRepresentation user = tuples.getT1();
                    UserAccessToken userToken = tuples.getT2();
                    return ServerResponse
                            .created(URI.create(String.valueOf(user.getId())))
                            .contentType(APPLICATION_JSON)
                            .bodyValue(userToken);
                });
    }

    public Mono<ServerResponse> handleUserLogin(ServerRequest loginRequest) {
        return loginRequest.bodyToMono(UserLoginRequest.class)
                .doOnNext(validator::validate)
                .flatMap(administrationService::signIn)
                .flatMap(userToken ->
                        ServerResponse
                                .ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(userToken));
    }

    public Mono<ServerResponse> handleUserRefreshToken(ServerRequest refreshTokenRequest) {
        return refreshTokenRequest.bodyToMono(UserRefreshTokenRequest.class)
                .doOnNext(validator::validate)
                .flatMap(administrationService::refreshToken)
                .flatMap(userToken ->
                        ServerResponse
                                .ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(userToken));
    }

    public Mono<ServerResponse> handleAboutMe(ServerRequest ignore) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getPrincipal() instanceof Jwt)
                .flatMap(authentication ->
                        ServerResponse
                                .ok()
                                .contentType(APPLICATION_JSON)
                                .body(administrationService.aboutMe((Jwt) authentication.getPrincipal()),
                                        AboutMeResponse.class));
    }

}
