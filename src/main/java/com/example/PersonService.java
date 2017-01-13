package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PersonService
{

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(final PersonRepository repository)
    {
        personRepository = repository;
    }

    Person save(Person person)
    {
        return personRepository.save(person);
    }
}
