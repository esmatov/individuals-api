package net.esmatov.individuals_api.exception;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String message) {
        super(message);
    }
}
