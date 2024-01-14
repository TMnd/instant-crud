package amaral.pt;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@Path("/api")
public class CrudResource {

    @Inject
    CrudService crudService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}")
    @Transactional
    public Response add(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic, String requestBody){
        boolean wasInserted = crudService.AddResource(apiKey, topic, requestBody);

        return (wasInserted) ?
                Response.ok(topic).build() :
                Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}")
    public Response getAll(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic) {
        List<Map<String, Object>> resources = crudService.getAllResource(apiKey, topic);

        return CollectionUtils.isNotEmpty(resources) ?
                Response.ok(resources).build() :
                Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}/{dataId}")
    @Transactional
    public Response getResource(@PathParam("topic") String topic, @PathParam("dataId") String id) {
        Map<String, Object> resource = crudService.getSingleResource(id, topic);

        return (resource != null) ?
                Response.ok(resource).build() :
                Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}/{id}")
    @Transactional
    public Response updateResource(@PathParam("topic") String topic, @PathParam("id") String id, String requestBody) {
        try {
            String updatedResourceId = this.crudService.updateResource(id, topic, requestBody);

            return Response.ok(updatedResourceId).build();
        } catch (JsonProcessingException e) {
            System.out.println(e); //log error

            return Response.serverError().build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}/{id}")
    @Transactional
    public Response deleteResource(@PathParam("topic") String topic, @PathParam("id") String id) {
        crudService.deleteResource(id, topic);

        return Response.ok().build();
    }

}
