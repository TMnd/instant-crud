package amaral.pt;

import amaral.pt.model.entity.Resource;
import amaral.pt.model.entity.ResourceId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;
import java.util.UUID;

@Path("/api")
public class CrudResource {

    private final ObjectMapper mapper = new ObjectMapper();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}")
    @Transactional
    public Response Add(
        @PathParam("apiKey") String apiKey,
        @PathParam("resource") String resource,
        String requestBody
    ){
        try {
            UUID uuid = UUID.randomUUID();
            Map<String, Object> bodyMap = mapper.readValue(requestBody, Map.class);

            ResourceId resourceId = new ResourceId(apiKey, resource);
            Resource topic = new Resource();
            topic.setResourceId(resourceId);
            topic.setData(bodyMap);
            topic.setDataId(uuid.toString());

            topic.persist();
        } catch (JsonProcessingException e) {
            System.out.println(e); //Todo add logger
            return Response.serverError().build();
        }

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
    @Path("/{apiKey}/{resource}/{id}")
    @Transactional
    public Response GetResource(
        @PathParam("apiKey") String apiKey,
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
