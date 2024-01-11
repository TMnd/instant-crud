package amaral.pt;

import amaral.pt.model.entity.Resource;
import amaral.pt.model.entity.ResourceId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api")
public class CrudResource {

    @Inject
    EntityManager entityManager;

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
            String uuid = UUID.randomUUID().toString();

            Map<String, Object> bodyMap = mapper.readValue(requestBody, Map.class);
            bodyMap.put("_id", uuid);

            ResourceId resourceId = new ResourceId(apiKey, resource);
            Resource topic = new Resource();
            topic.setResourceId(resourceId);
            topic.setData(bodyMap);
            topic.setDataId(uuid);

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
    @Path("/{apiKey}/{resource}/{dataId}")
    @Transactional
    public Response GetResource(
        @PathParam("apiKey") String apiKey,
        @PathParam("resource") String resource,
        @PathParam("dataId") String id
    ) {
        String sqlQuery = "SELECT data FROM resource WHERE data_id = :dataId and resource = :resource";

        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("dataId", id);
        query.setParameter("resource", resource);

        String result = String.valueOf(query.getSingleResult());

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
