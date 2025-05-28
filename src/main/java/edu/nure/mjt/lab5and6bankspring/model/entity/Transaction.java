package edu.nure.mjt.lab5and6bankspring.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//Клас-сутність об'єкта грошової транзакції (переказу грошових коштів)
@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    //Номер транзакції id, первин. ключ, автоінкремент
    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private Long transactionId;

    //Встановлення зв'язку між таблицями за полем from_account_id:
    //багато переказів коштів можна зробити з одного рахунку
    @ManyToOne
    @JoinColumn(name = "account_id_from")
    private BankAccount AccountFrom;

    //Встановлення зв'язку між таблицями за полем to_account_id:
    //багато переказів коштів можна зробити з одного рахунку
    @ManyToOne
    @JoinColumn(name = "account_id_to")
    private BankAccount AccountTo;

    //Розмір переказу
    @Column(name = "amount")
    private double amount;

    //Дата транзакції (переказу грошей)
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
}
