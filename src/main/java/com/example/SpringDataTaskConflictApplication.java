package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableTask
@Slf4j
public class SpringDataTaskConflictApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SpringDataTaskConflictApplication.class, args);
    }

    @Autowired
    PersonService personService;

    @Bean
    CommandLineRunner commandLineRunner()
    {
        return args ->
        {

            Person mary = new Person("Mary");
            Person bob = new Person("Bob");

            mary = personService.save(mary);
            bob = personService.save(bob);
        };
    }
}
