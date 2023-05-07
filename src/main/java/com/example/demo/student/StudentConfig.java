package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student m = new Student("Miloš", "miloš.ilić@gmail.com", LocalDate.of(1998, Month.JANUARY, 7));
            Student j = new Student("Jovan", "jovan.milić@gmail.com", LocalDate.of(1997, Month.FEBRUARY, 21));
            repository.saveAll(List.of(m, j));
        };
    }
}
