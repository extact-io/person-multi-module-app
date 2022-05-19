package io.extact.sample.person.webapi;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.extact.sample.person.entity.Person;

public interface PersonResouce {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Person get(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Person add(Person person);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Person> getAll();
}