package edu.nure.mjt.lab5and6bankspring;

//Пакети для запуску та автоматичного налаштування запуску додатку
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Анотація для вказання початку автоматичного налаштування додатку: підключення до БД, підключення до веб-сервісу тощо
@SpringBootApplication
public class Lab5and6BankSpringApplication {

    public static void main(String[] args) {
        //Метод для запуску додатку
        SpringApplication.run(Lab5and6BankSpringApplication.class, args);
    }

}
