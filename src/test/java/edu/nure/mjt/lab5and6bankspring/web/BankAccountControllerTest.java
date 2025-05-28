package edu.nure.mjt.lab5and6bankspring.web;

//Пакети з класами виключень, створених об'єктів для виконання банк. операцій
import edu.nure.mjt.lab5and6bankspring.exceptions.BankAccountNotFoundException;
import edu.nure.mjt.lab5and6bankspring.exceptions.ImpossibleMoneyTransferException;
import edu.nure.mjt.lab5and6bankspring.model.BankAccountDto;
import edu.nure.mjt.lab5and6bankspring.model.MoneyTransfer;
import edu.nure.mjt.lab5and6bankspring.model.UpdateBalance;
import edu.nure.mjt.lab5and6bankspring.service.BankAccountService;

//Паект для проведення тестів
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
//"Ін'єкція" (додавання) зробленого моку до всіх методів об'єкта вказаного класу
import org.mockito.InjectMocks;
//Створення моку (імітації) об'єкта
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
//Статичні методи для перевірки співпадіння отриманих результатів тестування з очікуваними
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
//Для налаштування моку
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;
    @InjectMocks
    private BankAccountController bankAccountController;

    //Перевірка методу отримання балансу на рахунку за вказаним id
    @Test
    public void getBalanceSuccess() {
        Long accountId = 12L;
        when(bankAccountService.getBankAccountBalance(accountId)).thenReturn(10.0);
        double result = bankAccountController.getBankAccountBalance(accountId);
        assertTrue(result == 10.0);
    }

    //Тестування методу повернення балансу на рахунку, якого не існує (перевірка, що повертається повідомлення з помилкою)
    @Test
    public void getBalanceIncorrectAccountId() {
        Long accountId = 12L;
        when(bankAccountService.getBankAccountBalance(accountId)).thenThrow(new BankAccountNotFoundException(accountId));
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountController.getBankAccountBalance(accountId));
    }

    //Тест методу для створення рахунку з порушенням у даних: нпрізвище не може мати знач. null
    @Test
    public void createAccountInvalidData() {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setAccountId(1L);
        bankAccountDto.setSurname(null);
        when(bankAccountService.createAccount(bankAccountDto)).thenThrow(new IllegalArgumentException("Invalid data"));
        //Перевірка, чи дійсно здійснення запиту на створення рахунку без прізвища до веб-сервера повертає виняток
        //класу IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> bankAccountController.createBankAccount(bankAccountDto));
    }

    @Test
    public void updateBalanceSuccess() {
        UpdateBalance updateBalance = new UpdateBalance();
        updateBalance.setAccountId(1L);
        updateBalance.setAmount(500.0);
        //Налаштування імітації об'єкта банк. сервісу на успішне виконання тесту без генерації винятків
        doNothing().when(bankAccountService).updateBalance(updateBalance);
        //Перевірка виконання методу без винятків, як і було заплановано вище
        assertDoesNotThrow(() -> bankAccountController.updateBalance(updateBalance));
    }

    //Перевірка методу видалення запису за вказаним id
    @Test
    public void deleteAccountSuccess() {
        Long accountId = 1L;
        doNothing().when(bankAccountService).deleteBankAccount(accountId);
        assertDoesNotThrow(() -> bankAccountController.deleteBankAccount(accountId));
    }

    //Перевірка методу переказу коштів між рахунками у разі, коли сума переказу більша за баланс картки відправника
    @Test
    public void makeImpossibleMoneyTransfer() {
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        moneyTransfer.setAccountIdFrom(1L);
        moneyTransfer.setAccountIdTo(2L);
        moneyTransfer.setAmountToTransfer(2000.0);
        //Налаштування моку сервісу банку так, щоб при виклику методу було згенеровано виняток
        //з помилкою через брак коштів на рахунку відправника (у нього лише 1000, а переказ складає 2000)
        doThrow(new ImpossibleMoneyTransferException(1000.0))
                .when(bankAccountService).transferMoney(moneyTransfer);
        assertThrows(ImpossibleMoneyTransferException.class, () -> bankAccountController.makeMoneyTransfer(moneyTransfer));
    }

    }