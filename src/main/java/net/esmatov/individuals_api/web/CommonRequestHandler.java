package net.esmatov.individuals_api.web;

import net.esmatov.individuals_api.client.http_body.UserAccessToken;
import net.esmatov.individuals_api.client.http_body.UserRepresentation;
import net.esmatov.individuals_api.dto.UserRegistrationRequest;
import net.esmatov.individuals_api.service.UserAdministrationService;
import net.esmatov.individuals_api.utils.Mappers;
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
                .map(Mappers::userRegistrationRequestToUserRepresentation)
                .flatMap(userAdministrationService::signUp)
                .flatMap(tuples -> {
                            UserRepresentation user = tuples.getT1();
                            UserAccessToken token = tuples.getT2();
                            return ServerResponse
                                    .created(URI.create(user.getId().toString()))
                                    .contentType(APPLICATION_JSON)
                                    .bodyValue(token);
                        }
                );
    }

}
