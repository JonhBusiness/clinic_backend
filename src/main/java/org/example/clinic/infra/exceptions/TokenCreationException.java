package org.example.clinic.infra.exceptions;

public class TokenCreationException extends TokenException {
    public TokenCreationException(String message) {
        super(message);
    }

    public TokenCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
