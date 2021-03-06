package com.hypnoticocelot.jaxrs.doclet.sample.resources;

import com.hypnoticocelot.jaxrs.doclet.sample.api.ModelResourceModel;
import com.hypnoticocelot.jaxrs.doclet.sample.api.THello;
import com.yammer.metrics.annotation.Timed;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ModelResource")
@Produces(MediaType.APPLICATION_JSON)
public class ModelResource {

    @Path("/{modelid}")
    @GET
    public ModelResourceModel getModel(@PathParam("modelid") long modelId) {
        return new ModelResourceModel(modelId, "Model Title", "Model Description", new DateTime());
    }

   @Path("/new")
    @PUT
    public ModelResourceModel addModel(@FormParam("model") ModelResourceModel model) {
        return model;
    }
}
