package amaral.pt.instantcrud;

import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import org.jboss.logging.Logger;
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

    private static final Logger LOG = Logger.getLogger(CrudResource.class);

    @Inject
    CrudService crudService;

    @Inject
    UriInfo uriInfo;

    @Inject
    CurrentVertxRequest request;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{topic}")
    @Transactional
    public Response add(@PathParam("topic") String topic, String requestBody){

        String origin = String.valueOf(uriInfo.getBaseUri());
        String apiKey = request.getCurrent().request().getHeader("x-api-key");

        boolean wasInserted = crudService.addResource(apiKey, topic, requestBody, origin);

        return (wasInserted) ?
                Response.ok(topic).build() :
                Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{topic}")
    public Response getAll(@PathParam("topic") String topic) {

        String origin = uriInfo.getBaseUri().toString();
        String apiKey = request.getCurrent().request().getHeader("x-api-key");

        List<Map<String, Object>> resources = crudService.getAllResource(apiKey, topic, origin);

        return CollectionUtils.isNotEmpty(resources) ?
                Response.ok(resources).build() :
                Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{topic}/{dataId}")
    @Transactional
    public Response getResource(@PathParam("topic") String topic, @PathParam("dataId") String id) {

        String origin = uriInfo.getBaseUri().toString();
        String apiKey = request.getCurrent().request().getHeader("x-api-key");

        Map<String, Object> resource = crudService.getSingleResource(apiKey, id, topic, origin);

        return (resource != null) ?
                Response.ok(resource).build() :
                Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{topic}/{dataId}")
    @Transactional
    public Response updateResource(@PathParam("topic") String topic, @PathParam("dataId") String dataId, String requestBody) {
        try {

            String origin = uriInfo.getBaseUri().toString();
            String apiKey = request.getCurrent().request().getHeader("x-api-key");
            String updatedResourceId = this.crudService.updateResource(apiKey, dataId, topic, requestBody, origin);

            if(updatedResourceId != null) {
                Response.ok(updatedResourceId).build();
            }
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }

        return Response.serverError().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{topic}/{dataId}")
    @Transactional
    public Response deleteResource(@PathParam("topic") String topic, @PathParam("dataId") String id) {

        String origin = uriInfo.getBaseUri().toString();
        String apiKey = request.getCurrent().request().getHeader("x-api-key");
        Long result = crudService.deleteResource(apiKey, id, topic, origin);

        return (result>0L) ?
            Response.ok().build():
            Response.serverError().build();
    }

}
