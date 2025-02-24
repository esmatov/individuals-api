package net.esmatov.individuals_api.client;

import net.esmatov.individuals_api.client.keycloak_model.UserAccessToken;
import net.esmatov.individuals_api.client.keycloak_model.UserRepresentation;
import net.esmatov.individuals_api.configuration.KeycloakConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

import static net.esmatov.individuals_api.utils.Tools.extractUserIdFromLocationHeader;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class KeycloakUserManagement {

    private final KeycloakConfigurationProperties kcProperties;
    private final WebClient kcWebClient;
    private final String USERS_PATH;
    private final String ACCESS_TOKEN_PATH;

    public KeycloakUserManagement(KeycloakConfigurationProperties kcProperties,
                                  @Qualifier("keycloakWebClient") WebClient kcWebClient) {
        this.kcProperties = kcProperties;
        this.kcWebClient = kcWebClient;
        this.ACCESS_TOKEN_PATH = String.format("/realms/%s/protocol/openid-connect/token", kcProperties.getRealm());
        this.USERS_PATH = String.format("/admin/realms/%s/users", kcProperties.getRealm());
    }

    public Mono<UserRepresentation> registerUser(UserRepresentation userRepresentation, String clientAccessToken) {
        return kcWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(USERS_PATH).build())
                .header(AUTHORIZATION, String.format("Bearer %s", clientAccessToken))
                .bodyValue(userRepresentation)
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                            UserRepresentation newUserRepresentation = new UserRepresentation(userRepresentation);
                            newUserRepresentation.setId(
                                    UUID.fromString(extractUserIdFromLocationHeader(
                                            Objects.requireNonNull(response.getHeaders().getLocation())
                                    ))
                            );
                            return newUserRepresentation;
                        }
                );
    }

    public Mono<UserAccessToken> requestUserAccessToken(String username, String password) {
        MultiValueMap<String, String> authFormData = new LinkedMultiValueMap<>();
        authFormData.add("grant_type", "password");
        authFormData.add("client_id", kcProperties.getAuthClientId());
        authFormData.add("client_secret", kcProperties.getAuthClientSecret());
        authFormData.add("username", username);
        authFormData.add("password", password);

        return kcWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(ACCESS_TOKEN_PATH).build())
                .body(BodyInserters.fromFormData(authFormData))
                .retrieve()
                .bodyToMono(UserAccessToken.class);
    }

    public Mono<UserAccessToken> requestUserAccessTokenByUserRefreshToken(String userRefreshToken) {
        MultiValueMap<String, String> authFormData = new LinkedMultiValueMap<>();
        authFormData.add("grant_type", "refresh_token");
        authFormData.add("client_id", kcProperties.getAuthClientId());
        authFormData.add("client_secret", kcProperties.getAuthClientSecret());
        authFormData.add("refresh_token", userRefreshToken);

        return kcWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(ACCESS_TOKEN_PATH).build())
                .body(BodyInserters.fromFormData(authFormData))
                .retrieve()
                .bodyToMono(UserAccessToken.class);
    }

}
