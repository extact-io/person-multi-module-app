package io.extact.sample.person.persistence;

import java.util.List;

import io.extact.sample.person.entity.Person;

public interface PersonRepository {
    Person get(long id);
    List<Person> findAll();
    Person add(Person person);
}
