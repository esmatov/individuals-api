package net.smatov.individuals_api.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import net.smatov.individuals_api.dto.ErrorResponse;
import net.smatov.individuals_api.dto.ValidationErrorResponse;
import net.smatov.individuals_api.exception.GenericErrorClientException;
import net.smatov.individuals_api.exception.InvalidUserTokenKeycloakException;
import net.smatov.individuals_api.exception.UnauthorizedUserKeycloakException;
import net.smatov.individuals_api.exception.UserConflictKeycloakException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestWebExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    public RestWebExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(APPLICATION_JSON);

        switch (throwable) {
            case ConstraintViolationException constraintViolation -> {
                response.setStatusCode(BAD_REQUEST);
                return writeValidationResponse(constraintViolation.getConstraintViolations(), response);
            }
            case UserConflictKeycloakException userAlreadyExists -> {
                response.setStatusCode(CONFLICT);
                return writeResponse(userAlreadyExists.getMessage(), response);
            }
            case UnauthorizedUserKeycloakException unauthorizedUser -> {
                response.setStatusCode(UNAUTHORIZED);
                return writeResponse(unauthorizedUser.getMessage(), response);
            }
            case InvalidUserTokenKeycloakException invalidUserToken -> {
                response.setStatusCode(UNAUTHORIZED);
                return writeResponse(invalidUserToken.getMessage(), response);
            }
            case GenericErrorClientException genericErrorClient -> {
                response.setStatusCode(SERVICE_UNAVAILABLE);
                return writeResponse(genericErrorClient.getMessage(), response);
            }
            default -> {
                return Mono.error(throwable);
            }
        }
    }

    private Mono<Void> writeResponse(String message, ServerHttpResponse response) {
        try {
            ErrorResponse errorResponse = new ErrorResponse(
                    requireNonNull(message), requireNonNull(response.getStatusCode()).value()
            );
            return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse)))
            );
        } catch (JsonProcessingException | NullPointerException e) {
            return response.setComplete();
        }
    }

    private Mono<Void> writeValidationResponse(Set<ConstraintViolation<?>> violations, ServerHttpResponse response) {
        try {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                    requireNonNull(response.getStatusCode()).value(),
                    violations.stream()
                            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                            .toList()
            );
            return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse)))
            );
        } catch (JsonProcessingException | NullPointerException e) {
            return response.setComplete();
        }
    }

}
