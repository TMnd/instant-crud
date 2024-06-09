package amaral.pt.apimgr;

import amaral.pt.apimgr.model.entity.Api;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.jboss.logging.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @Transactional
    public Response getAll() {
        List<Api> apiTokensList = this.apiService.getTokenList();

        return CollectionUtils.isNotEmpty(apiTokensList) ?
                Response.ok(apiTokensList).build() :
                Response.noContent().build();

    }
}
