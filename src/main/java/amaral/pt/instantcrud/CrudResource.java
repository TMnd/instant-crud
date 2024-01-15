package amaral.pt.instantcrud;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@Path("/crud")
public class CrudResource {

    @Inject
    CrudService crudService;

    @Inject
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}")
    @Transactional
    public Response add(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic, String requestBody){

        String origin = uriInfo.getBaseUri().toString();

        boolean wasInserted = crudService.AddResource(apiKey, topic, requestBody, origin);

        return (wasInserted) ?
                Response.ok(topic).build() :
                Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}")
    public Response getAll(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic) {

        String origin = uriInfo.getBaseUri().toString();

        List<Map<String, Object>> resources = crudService.getAllResource(apiKey, topic, origin);

        return CollectionUtils.isNotEmpty(resources) ?
                Response.ok(resources).build() :
                Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}/{dataId}")
    @Transactional
    public Response getResource(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic, @PathParam("dataId") String id) {

        String origin = uriInfo.getBaseUri().toString();

        Map<String, Object> resource = crudService.getSingleResource(apiKey, id, topic, origin);

        return (resource != null) ?
                Response.ok(resource).build() :
                Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}/{id}")
    @Transactional
    public Response updateResource(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic, @PathParam("id") String id, String requestBody) {
        try {

            String origin = uriInfo.getBaseUri().toString();

            String updatedResourceId = this.crudService.updateResource(apiKey, id, topic, requestBody, origin);

            return (updatedResourceId != null) ?
                    Response.ok(updatedResourceId).build():
                    Response.serverError().build();
        } catch (JsonProcessingException e) {
            System.out.println(e); //log error
            return Response.serverError().build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{apiKey}/{topic}/{id}")
    @Transactional
    public Response deleteResource(@PathParam("apiKey") String apiKey, @PathParam("topic") String topic, @PathParam("id") String id) {

        String origin = uriInfo.getBaseUri().toString();

        Long result = crudService.deleteResource(apiKey, id, topic, origin);

        return (result>0L) ?
            Response.ok().build():
            Response.serverError().build();
    }

}
