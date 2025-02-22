package net.esmatov.individuals_api.exception;

public class ClientErrorException extends RuntimeException {

    public ClientErrorException(String message) {
        super(message);
    }
}
