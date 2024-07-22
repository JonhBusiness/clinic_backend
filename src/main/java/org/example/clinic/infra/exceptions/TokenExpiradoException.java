package org.example.clinic.infra.exceptions;

public class TokenExpiradoException extends TokenException {
    public TokenExpiradoException(String message) {
        super(message);
    }

    public TokenExpiradoException(String message, Throwable cause) {
        super(message, cause);
    }
}