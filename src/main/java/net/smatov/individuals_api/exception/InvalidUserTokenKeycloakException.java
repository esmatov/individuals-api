package net.smatov.individuals_api.exception;

public class InvalidUserTokenKeycloakException extends RuntimeException {

    public InvalidUserTokenKeycloakException(String message) {
        super(message);
    }

    public static InvalidUserTokenKeycloakException withMessageInvalidUserRefreshToken() {
        return new InvalidUserTokenKeycloakException("Invalid or expired refresh token");
    }

}
