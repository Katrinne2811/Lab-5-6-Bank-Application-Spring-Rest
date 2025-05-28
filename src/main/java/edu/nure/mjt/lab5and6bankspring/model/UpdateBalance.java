package edu.nure.mjt.lab5and6bankspring.model;

import lombok.Data;

//Анотація для автоматичного підключення гетерів та сетерів для полів класу
@Data
//Клас об'єкту фінансової операції поповнення банк рахунку або зняття грошей з нього
public class UpdateBalance {

    //Поле id банк. акаунту клієнта, на якому буде оновлено баланс
    private Long accountId;

    //Сума для оновлення балансу
    private double amount;

}
