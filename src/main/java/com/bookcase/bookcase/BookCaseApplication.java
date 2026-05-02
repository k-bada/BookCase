package com.bookcase.bookcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookCaseApplication.class, args);
    }

}
