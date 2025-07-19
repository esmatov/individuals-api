package net.smatov.individuals_api.dto;

import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {

    private final List<String> details;

    public ValidationErrorResponse(int status, List<String> details) {
        super("Validation failed", status);
        this.details = details;
    }

    public List<String> getDetails() {
        return details;
    }

}
