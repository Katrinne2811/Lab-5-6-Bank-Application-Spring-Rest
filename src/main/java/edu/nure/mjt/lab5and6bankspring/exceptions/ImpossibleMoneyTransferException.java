package edu.nure.mjt.lab5and6bankspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class ImpossibleMoneyTransferException extends RuntimeException {
        public ImpossibleMoneyTransferException(double balance) {
            super("The balance is equal " + balance + "and does not allow to make transfer.");
    }
}
