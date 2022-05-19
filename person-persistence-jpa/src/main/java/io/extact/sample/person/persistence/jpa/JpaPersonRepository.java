package io.extact.sample.person.persistence.jpa;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.extact.sample.person.entity.Person;
import io.extact.sample.person.persistence.PersonRepository;

@ApplicationScoped
public class JpaPersonRepository implements PersonRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person get(long id) {
        return em.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        return em.createQuery("select p from Person p", Person.class).getResultList();
    }

    @Override
    public Person add(Person person) {
        em.persist(person);
        return person;
    }
}
