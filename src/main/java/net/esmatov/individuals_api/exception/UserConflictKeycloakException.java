package net.esmatov.individuals_api.exception;

public class UserConflictKeycloakException extends RuntimeException {

    private UserConflictKeycloakException(String message) {
        super(message);
    }

    public static UserConflictKeycloakException withMessageEmailAlreadyExists() {
        return new UserConflictKeycloakException("User with this email already exists");
    }

}
