package de.ass37.examples.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(value = {BadServiceCallException.class})
    public ResponseEntity<Object> handleApiRequestException(BadServiceCallException exception) {
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(resourceNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<Object> handleApiRequestException(NumberFormatException exception) {
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(resourceNotFoundException, HttpStatus.BAD_REQUEST);
    }
}
