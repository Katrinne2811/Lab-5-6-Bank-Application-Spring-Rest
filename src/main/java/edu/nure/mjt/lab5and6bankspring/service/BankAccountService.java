package edu.nure.mjt.lab5and6bankspring.service;//Імітація роботи банку

//Обробка виключень у разі відсутності запитаного рахунку за його id або від'ємного балансу на рахунку
import edu.nure.mjt.lab5and6bankspring.exceptions.BankAccountNotFoundException;
import edu.nure.mjt.lab5and6bankspring.exceptions.ImpossibleMoneyTransferException;
import edu.nure.mjt.lab5and6bankspring.exceptions.IncorrectAmountOfMoneyException;
//Класи для передачі даних про акаунт клієнта банку та оновлення його балансу
import edu.nure.mjt.lab5and6bankspring.model.*;
import edu.nure.mjt.lab5and6bankspring.model.entity.BankAccount;

import edu.nure.mjt.lab5and6bankspring.model.entity.Transaction;
import edu.nure.mjt.lab5and6bankspring.repository.BankAccountRepository;
//Клас для
import edu.nure.mjt.lab5and6bankspring.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Анотація для позначення класу як "керівника банку"
@Service
public class BankAccountService {

    //Поле - репозиторій з даними про банк. рахунки, що забезпечує зв'язок з БД та виконання CRUD-операції
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    //Поле для
    private final ModelMapper modelMapper;

    //Конструктор об'єкта сервісу банку
    public BankAccountService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    //Метод отримання балансу на рахунку клієнта з id, вказаним у дужках
    public double getBankAccountBalance(Long accountId) {

        return getBankAccount(accountId).getBalance();
    }

    private BankAccount getBankAccount(Long accountId) {
        //"Коробка", що повертає інформацію про рахунок у банку за його id, якщо такий рахунок існує
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        if (bankAccount.isEmpty()) {

            //Генерація виключення у разі відчсутності рахунку з вказаним id
            throw new BankAccountNotFoundException(accountId);
        } else {
            //Якщо акаунт (рахунок) знайдено, то отримується інформація про нього
            return bankAccount.get();
        }
    }

    //Метод створення повідомлення до веб-серверу (API) про створення рахунку з даними, переданими у bankAccountDto
    public BankAccountDto createAccount(BankAccountDto bankAccountDto) {
        //Створення рахунку з відомостями, отриманими із запиту до API у переданому об'єкті DTO
        BankAccount bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);

        BankAccount createdAccount = bankAccountRepository.save(bankAccount);

        return modelMapper.map(createdAccount, BankAccountDto.class);
    }

    //Метод для оновлення балансу на рахунку (акаунті) клієнта з вказаним id
    public void updateBalance(UpdateBalance updateBalance) {
        //Поле банківського акаунту (рахунку), для якого оновлюється баланс, отриманого за його id
        BankAccount bankAccount = getBankAccount(updateBalance.getAccountId());
        double newBalance = bankAccount.getBalance() + updateBalance.getAmount();
        if (newBalance < 0) {
            //Генерація виключення про помилку, якщо після оновлення баланс став від'ємним
            throw new IncorrectAmountOfMoneyException();
        } else
        {
            //Зміна значення поля балансу на рахунку bankAccount
            bankAccount.setBalance(newBalance);
        }
        bankAccountRepository.save(bankAccount);
    }

    //Метод для видалення рахунку клієнта з вказаним id
    public void deleteBankAccount(Long accountId) {
        BankAccount bankAccount = getBankAccount(accountId);
        bankAccountRepository.delete(bankAccount);
    }

    //Метод для переказу коштів з одного рахунку на інший з вказаними id
    public void transferMoney(MoneyTransfer moneyTransfer) {
        BankAccount bankAccountFrom = getBankAccount(moneyTransfer.getAccountIdFrom());
        BankAccount bankAccountTo = getBankAccount(moneyTransfer.getAccountIdTo());
        double transfer = moneyTransfer.getAmountToTransfer();
        if (bankAccountFrom.getBalance() < transfer) {
            throw new ImpossibleMoneyTransferException(bankAccountFrom.getBalance());
        }
        else {
            bankAccountFrom.setBalance(bankAccountFrom.getBalance() - transfer);
            bankAccountTo.setBalance(bankAccountTo.getBalance() + transfer);

            Transaction transaction = new Transaction();
            transaction.setAccountFrom(bankAccountFrom);
            transaction.setAccountTo(bankAccountTo);
            transaction.setAmount(transfer);
            transaction.setTransactionDate(LocalDateTime.now());

            //Додавання здійсненої транзакції до списку вхідних та вихідних транзакцій для рахунків, між якими відбувся переказ грошей
            bankAccountFrom.getOutgoingTransactions().add(transaction);
            bankAccountTo.getIncomingTransactions().add(transaction);
        }
        bankAccountRepository.save(bankAccountFrom);
        bankAccountRepository.save(bankAccountTo);
    }

    //Метод для отримання історії транзакцій (грошових переказів), в яких було задіяно
    //банк. рахунок з вказаним у дужках id
    public List<TransactionDto> getTransactionHistory(Long accountId) {
        BankAccount bankAccount = getBankAccount(accountId);
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(bankAccount.getOutgoingTransactions());
        allTransactions.addAll(bankAccount.getIncomingTransactions());
        //for (Transaction transaction : allTransactions) {
            //transactionRepository.save(transaction);
        //}
        //Перетворення списку транзакцій на список об'єктів типу DTO для передачі даних про транзакції
        return allTransactions.stream()
        .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
        .collect(Collectors.toList());
    }
}