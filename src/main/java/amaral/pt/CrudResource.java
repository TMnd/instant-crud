package amaral.pt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class CrudResource {

    String TEMP_API_KEY = "1";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{resource}")
    public Response Add(@PathParam("resource") String resource){
        System.out.println(resource);

        return Response.ok(resource).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{resource}")
    public Response GetAll(@PathParam("resource") String resource) {
        return Response.ok("all - " + resource).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{resource}/{id}")
    public Response GetResource(
        @PathParam("resource") String resource,
        @PathParam("id") String id
    ) {
        return Response.ok(resource + " - " + id).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{resource}/{id}")
    public Response UpdateResource(
            @PathParam("resource") String resource,
            @PathParam("id") String id
    ) {
        return Response.ok(resource + " - " + id).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{resource}/{id}")
    public Response DeleteResource(
            @PathParam("resource") String resource,
            @PathParam("id") String id
    ) {
        return Response.ok(resource + " - " + id).build();
    }

}
