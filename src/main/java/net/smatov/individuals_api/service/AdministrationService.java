package net.smatov.individuals_api.service;

import net.smatov.individuals_api.client.IdentityProviderClient;
import net.smatov.individuals_api.client.IdentityProviderUserManagement;
import net.smatov.individuals_api.client.KeycloakIdentityProviderClient;
import net.smatov.individuals_api.client.KeycloakIdentityProviderUserManagement;
import net.smatov.individuals_api.client.keycloak_model.UserAccessToken;
import net.smatov.individuals_api.client.keycloak_model.UserRepresentation;
import net.smatov.individuals_api.dto.AboutMeResponse;
import net.smatov.individuals_api.dto.UserLoginRequest;
import net.smatov.individuals_api.dto.UserRefreshTokenRequest;
import net.smatov.individuals_api.dto.UserRegistrationRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

import static net.smatov.individuals_api.utils.Mappers.userRegistrationRequestToUserRepresentation;
import static net.smatov.individuals_api.utils.Tools.extractUserAccountRolesFromJwt;

@Service
public class AdministrationService {

    private final IdentityProviderClient kcClient;
    private final IdentityProviderUserManagement kcUserManagement;

    public AdministrationService(KeycloakIdentityProviderClient kcClient,
                                 KeycloakIdentityProviderUserManagement kcUserManagement) {
        this.kcClient = kcClient;
        this.kcUserManagement = kcUserManagement;
    }

    public Mono<Tuple2<UserRepresentation, UserAccessToken>> signUp(UserRegistrationRequest registrationRequest) {
        return kcClient.obtainClientAccessToken()
                .flatMap(clientToken ->
                        kcUserManagement.registerUser(
                                userRegistrationRequestToUserRepresentation(registrationRequest),
                                clientToken.getAccessToken()
                        ))
                .zipWhen(user ->
                        kcUserManagement.requestUserAccessToken(
                                user.getUsername(),
                                user.getCredentials().getFirst().getValue()
                        ));
    }

    public Mono<UserAccessToken> signIn(UserLoginRequest loginRequest) {
        return kcUserManagement.requestUserAccessToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    public Mono<UserAccessToken> refreshToken(UserRefreshTokenRequest refreshTokenRequest) {
        return kcUserManagement.requestUserAccessTokenByUserRefreshToken(refreshTokenRequest.getRefreshToken());
    }

    public Mono<AboutMeResponse> aboutMe(Jwt jwt) {
        String userId = jwt.getSubject();
        String username = jwt.getClaim("preferred_username");
        String name = jwt.getClaim("name");
        String email = jwt.getClaim("email");
        List<String> roles = extractUserAccountRolesFromJwt(jwt.getClaimAsMap("resource_access"));
        return Mono.just(new AboutMeResponse(userId, username, name, email, roles));
    }

}
