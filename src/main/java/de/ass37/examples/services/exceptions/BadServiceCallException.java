package de.ass37.examples.services.exceptions;

public class BadServiceCallException extends RuntimeException {
    public BadServiceCallException(String message) {
        super(message);
    }

    public BadServiceCallException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
