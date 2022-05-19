package io.extact.sample.person.webapi;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import io.extact.sample.person.entity.Person;
import io.extact.sample.person.service.PersonService;

@ApplicationScoped
@Path("persons")
public class PersonResourceImpl implements PersonResouce {

    private PersonService service;

    @Inject
    public PersonResourceImpl(PersonService service) {
        this.service = service;
    }

    @Override
    public Person get(long id) {
        return service.get(id);
    }

    @Override
    public Person add(Person person) {
        return service.add(person);
    }

    @Override
    public List<Person> getAll() {
        return service.getAll();
    }
}
