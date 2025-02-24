package net.esmatov.individuals_api.service;

import net.esmatov.individuals_api.client.KeycloakClientApplication;
import net.esmatov.individuals_api.client.KeycloakUserManagement;
import net.esmatov.individuals_api.client.keycloak_model.UserAccessToken;
import net.esmatov.individuals_api.client.keycloak_model.UserRepresentation;
import net.esmatov.individuals_api.dto.AboutMeResponse;
import net.esmatov.individuals_api.dto.UserLoginRequest;
import net.esmatov.individuals_api.dto.UserRefreshTokenRequest;
import net.esmatov.individuals_api.dto.UserRegistrationRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static net.esmatov.individuals_api.utils.Mappers.userRegistrationRequestToUserRepresentation;
import static net.esmatov.individuals_api.utils.Tools.extractUserAccountRolesFromJwt;

@Service
public class UserAdministrationService {

    private final KeycloakClientApplication kcClientApp;
    private final KeycloakUserManagement kcUserManagement;

    public UserAdministrationService(KeycloakClientApplication kcClientApp, KeycloakUserManagement kcUserManagement) {
        this.kcClientApp = kcClientApp;
        this.kcUserManagement = kcUserManagement;
    }

    public Mono<Tuple2<UserRepresentation, UserAccessToken>> signUp(UserRegistrationRequest registrationRequest) {
        return kcClientApp.obtainClientAccessTokenWithCache()
                .flatMap(clientToken ->
                        kcUserManagement.registerUser(userRegistrationRequestToUserRepresentation(registrationRequest),
                                clientToken.getAccessToken()))
                .zipWhen(user ->
                        kcUserManagement.requestUserAccessToken(user.getUsername(),
                                user.getCredentials().getFirst().getValue()));
    }

    public Mono<UserAccessToken> signIn(UserLoginRequest loginRequest) {
        return kcUserManagement.requestUserAccessToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    public Mono<UserAccessToken> refreshToken(UserRefreshTokenRequest refreshTokenRequest) {
        return kcUserManagement.requestUserAccessTokenByUserRefreshToken(refreshTokenRequest.getRefreshToken());
    }

    public AboutMeResponse aboutMe(Jwt jwt) {
        String userId = jwt.getSubject();
        String username = jwt.getClaim("preferred_username");
        String name = jwt.getClaim("name");
        String email = jwt.getClaim("email");
        List<String> roles = extractUserAccountRolesFromJwt(jwt.getClaimAsMap("resource_access"));

        return new AboutMeResponse(userId, username, name, email, roles);
    }

}
