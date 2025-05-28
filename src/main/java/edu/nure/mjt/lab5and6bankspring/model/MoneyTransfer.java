package edu.nure.mjt.lab5and6bankspring.model;


import lombok.Data;

@Data
public class MoneyTransfer {

    //Поле id рахунку, з якого переказують кошти
    private Long accountIdFrom;

    //Поле id рахунку, на який переказують кошти
    private Long accountIdTo;

    //Розмір переказу коштів
    private double amountToTransfer;
}
