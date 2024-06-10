package amaral.pt.documentation;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/doc")
public class DocResource {

	@Inject
	Template index;

	@GET
	@Produces(value = MediaType.TEXT_HTML)
	public TemplateInstance get() {
		return index.data(null);
	}
}
