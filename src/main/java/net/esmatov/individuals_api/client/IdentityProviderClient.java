package net.esmatov.individuals_api.client;

import net.esmatov.individuals_api.client.keycloak_model.ClientAccessToken;
import reactor.core.publisher.Mono;

public interface IdentityProviderClient {

    Mono<ClientAccessToken> obtainClientAccessToken();

}
