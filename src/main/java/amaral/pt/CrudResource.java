package amaral.pt;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class CrudResource {

    @Inject
    CrudService crudService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}")
    @Transactional
    public Response Add(
        @PathParam("apiKey") String apiKey,
        @PathParam("resource") String resource,
        String requestBody
    ){

        boolean wasInserted = crudService.AddResource(apiKey, resource, requestBody);

        if(wasInserted) {
            return Response.ok(resource).build();
        }

        return Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}")
    public Response GetAll(
        @PathParam("apiKey") String apiKey,
        @PathParam("resource") String resource
    ) {

        return Response.ok("all - " + resource).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}/{dataId}")
    @Transactional
    public Response GetResource(
        @PathParam("resource") String resource,
        @PathParam("dataId") String id
    ) {
        String result = crudService.getSingleResource(id, resource);

        if(result != null) {
            return Response.ok(result).build();
        }

        return Response.noContent().build();
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
