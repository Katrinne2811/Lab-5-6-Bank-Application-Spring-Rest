package edu.nure.mjt.lab5and6bankspring.model;//Пакет, в якому мястяться всі інші пакети, класи,
//з яких складається банк. операції

//
import com.fasterxml.jackson.annotation.JsonProperty;
//Паекти з обмеженнями на значення полів (у відповідних колонках табл)
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
//Анотація з гетерами та сетерами для об'єкту банк. акаунту
import lombok.Data;
//Анотація для перевірки виконання обмежень для полів
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class BankAccountDto {

    //Обмеження, що при передачі даних про банк. акаунт через API можна лише читатти id акаунту
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long accountId;

    private String name;

    //Поле прізвища, що передається, не може мати значення null (тобто не можна передати об'єкт з null прізвищем)
    @NotNull
    private String surname;

    @NotNull
    //Телефон не може бути просто пробілом, а не тільки null
    @NotEmpty
    private String phoneNumber;

    //На балансі картки клієнта, про якого передається інформація, має бути невід'ємний баланс
    @Min(value=0)
    private double balance;
}
