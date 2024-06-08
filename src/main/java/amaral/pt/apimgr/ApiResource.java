package amaral.pt.apimgr;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.security.NoSuchAlgorithmException;

@Path("/api")
public class ApiResource {

    private static final Logger LOG = Logger.getLogger(ApiResource.class);

    @Inject
    ApiService apiService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    @Transactional
    public Response createApi(String requestBody){

	    try {
		    String apiKey  = this.apiService.createApiKey(requestBody);
		    return Response.ok(apiKey).build();
	    } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
	    }

	    return Response.serverError().build();
    }
}
