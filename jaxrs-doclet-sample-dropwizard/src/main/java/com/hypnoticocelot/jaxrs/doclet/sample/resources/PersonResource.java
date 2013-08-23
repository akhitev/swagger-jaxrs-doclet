package com.hypnoticocelot.jaxrs.doclet.sample.resources;


import com.hypnoticocelot.jaxrs.doclet.sample.api.TLocation;
import com.hypnoticocelot.jaxrs.doclet.sample.api.TPerson;
import com.hypnoticocelot.jaxrs.doclet.sample.api.TPersonName;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    @Path("persons/{uid}")
    @Timed
    public Response getPerson(@PathParam("uid") String uid) {
        TPerson tPerson = new TPerson( "123",
        "312",
        "ref",
        new TPersonName(),
        new TLocation(),
        "example.com/photo.jpg",
                Calendar.getInstance().getTimeInMillis());
        return Response.status(Response.Status.OK).entity(tPerson).build();
    }

  @POST
    @Path("persons")
    @Timed
    public Response addPerson(TPerson tPerson) {
    return Response.status(Response.Status.OK).entity(tPerson).build();
  }

  @PUT
  @Path("/persons/{id}")
  @Timed
  public Response updatePerson(@PathParam("id") String id, TPerson tPerson) {
          return Response.status(Response.Status.OK).entity(tPerson).build();
      }
}
