package net.esmatov.individuals_api.service;

import net.esmatov.individuals_api.client.KeycloakClientApplication;
import net.esmatov.individuals_api.client.KeycloakUserManagement;
import net.esmatov.individuals_api.client.http_body.UserAccessToken;
import net.esmatov.individuals_api.client.http_body.UserRepresentation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class UserAdministrationService {

    private final KeycloakClientApplication kcClientApp;
    private final KeycloakUserManagement kcUserManagement;

    public UserAdministrationService(KeycloakClientApplication kcClientApp, KeycloakUserManagement kcUserManagement) {
        this.kcClientApp = kcClientApp;
        this.kcUserManagement = kcUserManagement;
    }

    public Mono<Tuple2<UserRepresentation, UserAccessToken>> signUp(UserRepresentation userRepresentation) {
        return kcClientApp.obtainClientAccessTokenWithCache()
                .flatMap(clientAccessToken ->
                        kcUserManagement.registerUser(userRepresentation, clientAccessToken.getAccessToken()))
                .zipWhen(user ->
                        kcUserManagement.requestUserAccessToken(userRepresentation.getUsername(),
                                userRepresentation.getCredentials().getFirst().getValue())
                );
    }

}
