package io.extact.sample.person.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.sample.person.entity.Person;
import io.extact.sample.person.persistence.jpa.junit5.JpaTransactionalExtension;
import io.extact.sample.person.persistence.jpa.junit5.TransactionalForTest;

@ExtendWith(JpaTransactionalExtension.class)
public class JpaPersonRepositoryTest {

    private JpaPersonRepository repository;

    @BeforeEach
    void setup(EntityManager em) {
        repository = new JpaPersonRepository(em);
    }

    @Test
    void tesGet() {
        var expected = new Person(1L, "soramame", 18);
        var actual = repository.get(1L);
        assertEquals(expected, actual);
    }

    @Test
    void tesGetAll() {
        var actual = repository.findAll();
        assertEquals(2, actual.size());
    }

    @Test
    @TransactionalForTest
    void testAdd() {
        var expected = new Person(null, "test", 99);
        var actual = repository.add(expected);
        expected.setId(3L);
        assertEquals(expected, actual);
    }
}
