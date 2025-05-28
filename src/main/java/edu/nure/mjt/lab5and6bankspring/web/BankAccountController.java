package edu.nure.mjt.lab5and6bankspring.web;//Пакет з класом для прийняття запитів від клієнтів банку на виконання фінанс операцій

//Класи для передачі інформації про рахунок та зміни у ньому, клас сервісу банку
import edu.nure.mjt.lab5and6bankspring.model.*;
import edu.nure.mjt.lab5and6bankspring.service.BankAccountService;
//Перевірка виконання заданих умов
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Анотація для визначення класу як приймача запитів від клієнтів банку через API
@RestController
public class BankAccountController {

    //Стале поле сервісу банку
    private final BankAccountService bankAccountService;

    //Конструктор сервісу банку
    public BankAccountController(BankAccountService service) {
        bankAccountService = service;
    }

    //Анотація для повернення відповіді на запит про рахунок у банку за його id
@GetMapping("/{accountId}")
    //Операція отримання значення балансу на рахунку за id
    public double getBankAccountBalance(@PathVariable Long accountId) {

    return bankAccountService.getBankAccountBalance(accountId);
}

//Анотація для прийняття запиту на створення запису таблиці account
@PostMapping("/account")
    //Об'єкт передачі інформації ("лист") з даними про акаунт (рахунок) клієнта банку
    public BankAccountDto createBankAccount(@Valid @RequestBody BankAccountDto bankAccount) {

        return bankAccountService.createAccount(bankAccount);
}

//Анотація на виконання запиту у API на оновлення балансу рахунку за адресою /update/balance
@PutMapping("/update/balance")
    //Метод оновлення балансу на рахунку, що вказано у об'єкті передачі інформації ("листі" до API)
    public void updateBalance(@Valid @RequestBody UpdateBalance updateBalance) {
    bankAccountService.updateBalance(updateBalance);
}

  //Анотація на виконання запиту про видалення банківського рахунку з вказаним id
  @DeleteMapping("/delete/{account_id}")
  //Метод видалення банківського рахунку з БД за вказаним id
  public void deleteBankAccount(@PathVariable Long accountId)
  {
      bankAccountService.deleteBankAccount(accountId);
  }

    //Додатковий функціонал: операція переказу коштів з одного рахунку на інший
    //Анотація для прийняття запиту на переказ коштів
    @PutMapping("/transfer")
    //Об'єкт передачі інформації ("лист") з даними про акаунт (рахунок) клієнта банку
    public void makeMoneyTransfer(@Valid @RequestBody MoneyTransfer moneyTransfer) {

         bankAccountService.transferMoney(moneyTransfer);
    }

    //Анотація для виконання запиту до сервера на отримання переліку транзакцій, пов'язаних з рахунком за його id
    @GetMapping("/account/{accountId}/transactions")
    public List<TransactionDto> getTransactionHistory(@PathVariable Long accountId) {
        List<TransactionDto> transactions = bankAccountService.getTransactionHistory(accountId);
        return transactions;
    }
}

