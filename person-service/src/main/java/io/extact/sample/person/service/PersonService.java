package io.extact.sample.person.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.extact.sample.person.entity.Person;
import io.extact.sample.person.persistence.PersonRepository;

@ApplicationScoped
@Transactional
public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
    private PersonRepository repository;

    @Inject
    public PersonService(PersonRepository repository) {
        this.repository = repository;
        LOG.debug("instantiated");
    }

    public Person get(long id) {
        return repository.get(id);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Person add(Person person) {
        return repository.add(person);
    }
}
