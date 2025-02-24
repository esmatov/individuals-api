package net.esmatov.individuals_api.client;

import net.esmatov.individuals_api.client.keycloak_model.ClientAccessToken;
import net.esmatov.individuals_api.configuration.KeycloakConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class KeycloakClientApplication {

    private final KeycloakConfigurationProperties kcProperties;
    private final WebClient kcWebClient;
    private final AtomicReference<Mono<ClientAccessToken>> clientAccessTokenMonoAtomRef =
            new AtomicReference<>(Mono.empty());
    private final String ACCESS_TOKEN_PATH;

    public KeycloakClientApplication(KeycloakConfigurationProperties kcProperties,
                                     @Qualifier("keycloakWebClient") WebClient kcWebClient) {
        this.kcProperties = kcProperties;
        this.kcWebClient = kcWebClient;
        this.ACCESS_TOKEN_PATH = String.format("/realms/%s/protocol/openid-connect/token", kcProperties.getRealm());
    }

    public Mono<ClientAccessToken> obtainClientAccessTokenWithCache() {
        return clientAccessTokenMonoAtomRef.get()
                .switchIfEmpty(
                        Mono.defer(() -> {
                            Mono<ClientAccessToken> tokenMono = requestClientAccessToken()
                                    .cache(
                                            token -> Duration.ofSeconds(token.getExpiresIn() - 10L),
                                            exc -> Duration.ZERO,
                                            () -> Duration.ZERO
                                    );
                            return clientAccessTokenMonoAtomRef.compareAndSet(Mono.empty(), tokenMono) ?
                                    tokenMono : clientAccessTokenMonoAtomRef.get();
                        })
                );
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
                .bodyToMono(ClientAccessToken.class);
    }

}
