package io.extact.sample.person.webapi;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.extact.sample.person.entity.Person;

public abstract class PersonResourceBaseTest {
    private PersonResouce personResource;
    @BeforeEach
    public void setup() throws Exception {
        personResource = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:7001/api/persons"))
                .build(PersonResouce.class);
    }
    @Test
    @Order(1)
    void tesGetPerson() {
        var expected = new Person(1L, "soramame", 18);
        var actual = personResource.get(1L);
        assertEquals(expected, actual);
    }
    @Test
    @Order(2)
    void tesGetAll() {
        var actual = personResource.getAll();
        assertEquals(2, actual.size());
    }
    @Test
    @Order(3)
    void tesAdd() {
        var expected = new Person(null, "test", 99);
        var actual = personResource.add(expected);
        expected.setId(3L);
        assertEquals(expected, actual);
    }
    @Test
    @Order(4)
    void tesGetPersonAfterAddPerson() {
        var expected = new Person(3L, "test", 99);
        var actual = personResource.get(3L);
        assertEquals(expected, actual);
    }

    public static class DisableAnotherPersistenceBeanExtension implements Extension {
        void disenable(@Observes ProcessAnnotatedType<?> event) {
            var config = ConfigProvider.getConfig();
            Class<?> disableBean = config.getValue("disable.bean", Class.class);
            if (event.getAnnotatedType().getJavaClass() == disableBean) {
                event.veto();
            }
        }
    }
}
