package amaral.pt.apimgr;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class ApiResource {

    @Inject
    ApiService apiService;

    // create api key
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    @Transactional
    public Response createApi(String requestBody){
        String apiKey = this.apiService.createApiKey(requestBody);
        return Response.ok(apiKey).build();
    }
}
