package net.smatov.individuals_api.client;

import net.smatov.individuals_api.client.keycloak_model.UserAccessToken;
import net.smatov.individuals_api.client.keycloak_model.UserRepresentation;
import reactor.core.publisher.Mono;

public interface IdentityProviderUserManagement {

    Mono<UserRepresentation> registerUser(UserRepresentation userRepresentation, String clientAccessToken);

    Mono<UserAccessToken> requestUserAccessToken(String username, String password);

    Mono<UserAccessToken> requestUserAccessTokenByUserRefreshToken(String userRefreshToken);

}
