package net.smatov.person_service.handler;

import net.smatov.person_service.client.exception.CountryNotFoundException;
import net.smatov.person_service.client.exception.ServiceUnavailableException;
import net.smatov.person_service.exception.IndividualAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<?> countryNotFoundExceptionHandler(CountryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> serviceUnavailableExceptionHandler(ServiceUnavailableException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
    }

    @ExceptionHandler(IndividualAlreadyExistsException.class)
    public ResponseEntity<?> individualAlreadyExistsExceptionHandler(IndividualAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
