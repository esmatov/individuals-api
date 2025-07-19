package net.smatov.individuals_api.exception;

public class UnauthorizedUserKeycloakException extends RuntimeException {

    private UnauthorizedUserKeycloakException(String message) {
        super(message);
    }

    public static UnauthorizedUserKeycloakException withMessageInvalidUserCredentials() {
        return new UnauthorizedUserKeycloakException("Invalid user credentials");
    }

}
