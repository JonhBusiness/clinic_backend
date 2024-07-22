package org.example.clinic.infra.exceptions;

public class TokenMalFormadoException extends TokenException {
    public TokenMalFormadoException(String message) {
        super(message);
    }

    public TokenMalFormadoException(String message, Throwable cause) {
        super(message, cause);
    }
}