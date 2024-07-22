package org.example.clinic.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class ValidacionException  extends RuntimeException {

    public ValidacionException(String message) {
        super(message);
    }
}