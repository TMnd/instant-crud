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
    @Path("/{apiKey}/{resource}")
    @Transactional
    public Response add(@PathParam("apiKey") String apiKey, @PathParam("resource") String resource, String requestBody){
        boolean wasInserted = crudService.AddResource(apiKey, resource, requestBody);

        return (wasInserted) ?
                Response.ok(resource).build() :
                Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}")
    public Response getAll(@PathParam("apiKey") String apiKey, @PathParam("resource") String resource) {
        List<Map<String, Object>> allResource = crudService.getAllResource(apiKey, resource);

        return CollectionUtils.isNotEmpty(allResource) ?
                Response.ok(allResource).build() :
                Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}/{dataId}")
    @Transactional
    public Response getResource(@PathParam("resource") String resource, @PathParam("dataId") String id) {
        Map<String, Object> singleResource = crudService.getSingleResource(id, resource);

        return (singleResource != null) ?
                Response.ok(singleResource).build() :
                Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}/{id}")
    @Transactional
    public Response updateResource(@PathParam("resource") String resource, @PathParam("id") String id, String requestBody) {
        try {
            String updatedResourceId = this.crudService.updateResource(id, resource, requestBody);
            return Response.ok(updatedResourceId).build();
        } catch (JsonProcessingException e) {
            System.out.println(e); //log error
            return Response.serverError().build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{resource}/{id}")
    @Transactional
    public Response deleteResource(@PathParam("resource") String resource, @PathParam("id") String id) {
        crudService.deleteResource(id, resource);

        return Response.ok().build();
    }

}
