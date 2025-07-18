package net.smatov.person_service.client.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException() {
        super("Service unavailable");
    }

}
