package net.smatov.individuals_api.client;

import net.smatov.individuals_api.client.keycloak_model.ClientAccessToken;
import net.smatov.individuals_api.configuration.KeycloakConfigurationProperties;
import net.smatov.individuals_api.exception.GenericErrorClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class KeycloakIdentityProviderClient implements IdentityProviderClient {

    private final KeycloakConfigurationProperties kcProperties;
    private final WebClient kcWebClient;
    private final String ACCESS_TOKEN_PATH;
    private final Mono<ClientAccessToken> clientAccessTokenMonoWithCache;

    public KeycloakIdentityProviderClient(KeycloakConfigurationProperties kcProperties,
                                          @Qualifier("keycloakWebClient") WebClient kcWebClient) {
        this.kcProperties = kcProperties;
        this.kcWebClient = kcWebClient;
        this.ACCESS_TOKEN_PATH = String.format("/realms/%s/protocol/openid-connect/token", kcProperties.getRealm());
        this.clientAccessTokenMonoWithCache = Mono.defer(() -> requestClientAccessToken()
                .cache(token -> Duration.ofSeconds(token.getExpiresIn() - 10L),
                        exception -> Duration.ZERO,
                        () -> Duration.ZERO));
    }

    @Override
    public synchronized Mono<ClientAccessToken> obtainClientAccessToken() {
        return clientAccessTokenMonoWithCache;
    }

    private Mono<ClientAccessToken> requestClientAccessToken() {
        MultiValueMap<String, String> authFormData = new LinkedMultiValueMap<>();
        authFormData.add("grant_type", "client_credentials");
        authFormData.add("client_id", kcProperties.getAuthClientId());
        authFormData.add("client_secret", kcProperties.getAuthClientSecret());
        return kcWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(ACCESS_TOKEN_PATH).build())
                .body(BodyInserters.fromFormData(authFormData))
                .retrieve()
                .bodyToMono(ClientAccessToken.class)
                .onErrorMap(throwable -> GenericErrorClientException.withMessageServiceUnavailable());
    }

}
