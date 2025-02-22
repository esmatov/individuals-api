package net.esmatov.individuals_api.utils;

import net.esmatov.individuals_api.client.http_body.UserRepresentation;
import net.esmatov.individuals_api.dto.UserRegistrationRequest;

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
