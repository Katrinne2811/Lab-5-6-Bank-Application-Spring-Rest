package edu.nure.mjt.lab5and6bankspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(Long id) {
        super("Could not find account with id " + id);
    }
}
