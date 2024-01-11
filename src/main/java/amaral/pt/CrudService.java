package amaral.pt;

import amaral.pt.model.entity.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class CrudService {

    @Inject
    EntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper();

    public boolean AddResource(
        String apiKey,
        String resource,
        String requestBody
    ) {
        try {
            String uuid = UUID.randomUUID().toString();

            Map<String, Object> bodyMap = mapper.readValue(requestBody, Map.class);
            bodyMap.put("_id", uuid);

            Resource topic = new Resource();
            topic.setDataId(uuid);
            topic.setApikey(apiKey);
            topic.setResource(resource);
            topic.setData(bodyMap);

            topic.persist();
        } catch (JsonProcessingException e) {
            System.out.println(e); //Todo add logger
            return false;
        }

        return true;
    }

    public String getSingleResource(
        String resource,
        String id
    ){

        TypedQuery<Resource> query = entityManager.createQuery("SELECT * FROM Resource", Resource.class);
        List<Resource> resources = query.getResultList();
        System.out.println(resources);

        return String.valueOf(query.getSingleResult());
    }
}
