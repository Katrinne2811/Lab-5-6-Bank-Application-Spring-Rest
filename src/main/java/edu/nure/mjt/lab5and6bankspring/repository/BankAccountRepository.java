package edu.nure.mjt.lab5and6bankspring.repository;

import org.springframework.data.repository.CrudRepository;
import edu.nure.mjt.lab5and6bankspring.model.entity.BankAccount;
import org.springframework.stereotype.Repository;

//Анотація для сховища, де зберігається база даних з акаунтами клієнтів
@Repository
//"Контракт" з правилами зберігання бази даних з інформацією про рахунки клієнтів банку
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

}
