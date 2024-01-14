package amaral.pt;

import amaral.pt.model.entity.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper = new ObjectMapper();

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
    @Path("/{resource}/{id}")
    public Response updateResource(
            @PathParam("resource") String resource,
            @PathParam("id") String id
    ) {

        return Response.ok(resource + " - " + id).build();
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
