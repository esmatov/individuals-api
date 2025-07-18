package net.smatov.person_service.client.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException() {
        super("Country not found");
    }

}
