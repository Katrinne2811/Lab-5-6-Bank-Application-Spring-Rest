package edu.nure.mjt.lab5and6bankspring.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    //Поля з даними про транзакцію: номер транзакції, рахунки, між якими відбувся переказ коштів,
    //сума переказу та дата
    private Long transactionId;
    private Long AccountIdFrom;
    private Long AccountIdTo;
    private double amount;
    private LocalDateTime transactionDate;
}
