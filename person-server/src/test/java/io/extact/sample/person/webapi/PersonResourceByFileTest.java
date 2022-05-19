package io.extact.sample.person.webapi;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.AddExtension;
import io.helidon.microprofile.tests.junit5.HelidonTest;

import io.extact.sample.person.webapi.PersonResourceBaseTest.DisableAnotherPersistenceBeanExtension;

@HelidonTest
@AddConfig(key = "server.port", value = "7001")
@AddConfig(key = "disable.bean", value = "io.extact.sample.person.persistence.jpa.JpaPersonRepository")
@AddExtension(DisableAnotherPersistenceBeanExtension.class)
@ExtendWith(JulToSLF4DelegateExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class PersonResourceByFileTest extends PersonResourceBaseTest {
}
