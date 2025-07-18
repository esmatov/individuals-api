package net.smatov.person_service.exception;

public class IndividualAlreadyExistsException extends RuntimeException {

    public IndividualAlreadyExistsException() {
        super("Individual already exists");
    }

}
