package edu.nure.mjt.lab5and6bankspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectAmountOfMoneyException extends RuntimeException {

}
