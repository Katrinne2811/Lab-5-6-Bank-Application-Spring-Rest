package edu.nure.mjt.lab5and6bankspring;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Анотація, що визначає клас як налаштування банку
@Configuration
public class BankConfiguration {

    //Анотація для визначення методу getMapper як інструменту фреймворку Spring для переносу (мапінгу)
    // інформації з об'єкту dto ("листа") до об'єктів Java
    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }

}
