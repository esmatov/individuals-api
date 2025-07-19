package net.smatov.individuals_api.utils;

import net.smatov.individuals_api.client.keycloak_model.UserRepresentation;
import net.smatov.individuals_api.dto.UserRegistrationRequest;

public class Mappers {

    public static UserRepresentation userRegistrationRequestToUserRepresentation
            (UserRegistrationRequest userRegistrationRequest) {
        UserRepresentation userRepresentation = new UserRepresentation
                (null, userRegistrationRequest.getUsername(), userRegistrationRequest.getFirstName(),
                        userRegistrationRequest.getLastName(), userRegistrationRequest.getEmail(),
                        true, true, null,
                        null, null, null);
        userRepresentation.addPassword(userRegistrationRequest.getPassword(), false);
        return userRepresentation;
    }

}
