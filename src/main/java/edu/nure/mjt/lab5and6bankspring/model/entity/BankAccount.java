package edu.nure.mjt.lab5and6bankspring.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//Клас-сутність: клас, що мапінгується з таблицею account в БД
//Клас акаунту (рахунку) клієнта банку
@Entity
@Table(name="account")
//Анотація, що автоматично викликає методи гетери та сетери для отримання властивостей об'єкта та зміни
@Data
public class BankAccount {
    //Номер акаунту в БД (первинний ключ), генерується автоматично у БД, відповідає колонці account_id
    //у табл. account
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long accountId;

    //Поля даних клієнтів банку: ім'я, прізвище, номер телефона та баланс на рахунку
    private String name;
    private String surname;
    private String phoneNumber;
    private double balance;

    //Зовнішні ключі для зв'язків між таблицями transaction та account":
    //з одним рахунком можуть бути здійснені багато транзакцій (як на відправку, так і зарахування коштів)
    @OneToMany(mappedBy = "AccountFrom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> outgoingTransactions = new ArrayList<>();
    @OneToMany(mappedBy = "AccountTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> incomingTransactions = new ArrayList<>();
}
