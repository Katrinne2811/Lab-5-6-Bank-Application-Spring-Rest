package edu.nure.mjt.lab5and6bankspring.repository;

import edu.nure.mjt.lab5and6bankspring.model.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

//Інтерфейс для збереження даних про транзакції з їх id з можливістю виконання CRUD-операцій над цими даними
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
