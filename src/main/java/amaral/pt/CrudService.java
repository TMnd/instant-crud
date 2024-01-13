package amaral.pt;

import amaral.pt.model.entity.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Set;

//@Path("/api")
@ApplicationScoped
@RegisterRestClient(configKey = "instant-crud")
public interface CrudService{

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/{apiKey}/{resource}")
    Response addResource(Resource resource);

//    @GET
//    @Path("/stream/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
    Set<Resource> getById(@QueryParam("id") String id);
}
