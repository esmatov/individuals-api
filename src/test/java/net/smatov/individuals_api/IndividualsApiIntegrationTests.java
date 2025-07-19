package net.smatov.individuals_api;

import com.fasterxml.jackson.databind.JsonNode;
import net.smatov.individuals_api.client.keycloak_model.ClientAccessToken;
import net.smatov.individuals_api.configuration.KeycloakConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;

import static java.util.Objects.nonNull;
import static net.smatov.individuals_api.utils.RequestTestObjects.*;
import static net.smatov.individuals_api.web.GlobalRouterConfiguration.*;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestEnvironmentConfiguration.class)
class IndividualsApiIntegrationTests {

    @Autowired
    KeycloakConfigurationProperties kcProperties;
    @Autowired
    WebTestClient appTestClient;
    @Autowired
    @Qualifier("keycloakTestClient")
    WebClient kcTestClient;

    @Test
    void signUp_WithValidRequest_ShouldReturnStatusIsCreated() {
        String userIdPostSignUp = appTestClient.post()
                .uri(REGISTRATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                        validUserTonyHawkRegistrationData()
                )
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(Void.class)
                .getResponseHeaders()
                .getFirst("location");

        String clientAccessToken = requestClientAccessToken();
        String userIdFromKeycloak = kcTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("admin/realms/{realm}/users/{userId}")
                                .build(kcProperties.getRealm(), userIdPostSignUp))
                .header("Authorization", "Bearer " + clientAccessToken)
                .retrieve()
                .bodyToMono(JsonNode.class).block()
                .get("id").asText();

        assert isNotBlank(userIdFromKeycloak);
    }

    @Test
    void signIn_WithValidRequest_ShouldReturnBearerTokens() {
        String clientAccessToken = requestClientAccessToken();
        ResponseEntity<JsonNode> userCreationResult = kcTestClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path("admin/realms/{realm}/users")
                                .build(kcProperties.getRealm()))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientAccessToken)
                .bodyValue(
                        validUserNinjaTurtleRegistrationData()
                )
                .retrieve()
                .toEntity(JsonNode.class).block();
        assert userCreationResult.getStatusCode().is2xxSuccessful();

        appTestClient.post()
                .uri(LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(
                        validUserNinjaTurtleLoginData()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.access_token")
                .isNotEmpty();
    }

    @Test
    void refreshToken_WithValidRequest_ShouldReturnFreshBearerTokens() {
        String clientAccessToken = requestClientAccessToken();
        ResponseEntity<JsonNode> userCreationResult = kcTestClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path("admin/realms/{realm}/users")
                                .build(kcProperties.getRealm()))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientAccessToken)
                .bodyValue(
                        validUserWalterWhiteRegistrationData()
                )
                .retrieve()
                .toEntity(JsonNode.class).block();
        assert userCreationResult.getStatusCode().is2xxSuccessful();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "heisenberg");
        formData.add("password", "saymyname");
        formData.add("grant_type", "password");
        formData.add("client_id", "individuals-api");
        formData.add("client_secret", "54wCiwxzPESBMixFk8fajFz8sZkSSL6b");
        String userRefreshToken = kcTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("realms/{realm}/protocol/openid-connect/token")
                        .build(kcProperties.getRealm()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> response.path("refresh_token").asText())
                .block();
        assert isNotBlank(userRefreshToken);

        appTestClient.post()
                .uri(REFRESH_TOKEN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new HashMap<>() {{
                    put("refreshToken", userRefreshToken);
                }})
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.access_token")
                .isNotEmpty();
    }

    @Test
    void aboutMe_WithValidRequest_ShouldReturnUserInfo() {
        String clientAccessToken = requestClientAccessToken();
        ResponseEntity<JsonNode> userCreationResult = kcTestClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path("admin/realms/{realm}/users")
                                .build(kcProperties.getRealm()))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientAccessToken)
                .bodyValue(
                        validUserAndreyTarkovskyRegistrationData()
                )
                .retrieve()
                .toEntity(JsonNode.class).block();
        assert userCreationResult.getStatusCode().is2xxSuccessful();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "andrey_tarkovsky");
        formData.add("password", "solaris1972");
        formData.add("grant_type", "password");
        formData.add("client_id", kcProperties.getAuthClientId());
        formData.add("client_secret", kcProperties.getAuthClientSecret());
        String userAccessToken = kcTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("realms/{realm}/protocol/openid-connect/token")
                        .build(kcProperties.getRealm()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> response.path("access_token").asText())
                .block();

        appTestClient.get()
                .uri(ABOUT_ME)
                .header("Authorization", "Bearer " + userAccessToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.userId")
                .isNotEmpty();
        ;
    }


    private String requestClientAccessToken() {
        MultiValueMap<String, String> authFormData = new LinkedMultiValueMap<>();
        authFormData.add("grant_type", "client_credentials");
        authFormData.add("client_id", kcProperties.getAuthClientId());
        authFormData.add("client_secret", kcProperties.getAuthClientSecret());

        String clientAccessToken = kcTestClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path("realms/" + kcProperties.getRealm() + "/protocol/openid-connect/token")
                                .build())
                .body(BodyInserters.fromFormData(authFormData))
                .retrieve()
                .bodyToMono(ClientAccessToken.class)
                .block()
                .getAccessToken();

        assert nonNull(clientAccessToken);
        return clientAccessToken;
    }

}
