package net.smatov.individuals_api.exception;

public class GenericErrorClientException extends RuntimeException {

    private GenericErrorClientException(String message) {
        super(message);
    }

    public static GenericErrorClientException withMessageServiceUnavailable() {
        return new GenericErrorClientException("Service temporarily unavailable");
    }

}
