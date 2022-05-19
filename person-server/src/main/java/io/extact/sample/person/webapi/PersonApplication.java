package io.extact.sample.person.webapi;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("api")
public class PersonApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(PersonResourceImpl.class);
    }
}
