package com.hypnoticocelot.jaxrs.doclet.sample.resources;

import com.hypnoticocelot.jaxrs.doclet.sample.api.THello;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

// JSON for Thello {"id":"agora","value":"value","setId":true,"setValue":true}
/* model json for Thrift entity should be
"THello" : {
"id " : "THello",
properties" : {
        "id" : {
          "type" : "string"
        },
        "value" : {
          "type" : "string"
        }
}

###########
now it is generated as =>
"models" : {
    "THello" : {
      "id" : "THello",
      "properties" : {
        "id" : {
          "type" : "string"
        },
        TODO remove folowing 2 fields
        "metaDataMap" : {
          "type" : "Map",
          "items" : {
            "$ref" : "_Fields"
          }
        },
        "fieldValue" : {
          "type" : "object"
        },
        "value" : {
          "type" : "string"
        }
      }
    },  TODO remove this model at all
    "_Fields" : {
      "id" : "_Fields",
      "properties" : {
        "fieldName" : {
          "type" : "string"
        },
        "thriftFieldId" : {
          "type" : "short"
        }
      }
    }


*/


@PUT
@Path("/hellos/{id}")
@Timed
public Response updateHello(@PathParam("id") String id, THello tHello) {
        return Response.status(Response.Status.OK).entity(tHello).build();
    }

  @POST
    @Path("hellos")
    @Timed
    public Response addHello(THello tHello) {
    return Response.status(Response.Status.OK).entity(tHello).build();
  }

  @GET
  @Path("/hellos/{id}")
  @Timed
  public Response getHello(@PathParam("id") String id, THello tHello) {
          return Response.status(Response.Status.OK).entity(tHello).build();
      }
}
