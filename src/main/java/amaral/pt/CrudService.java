package amaral.pt;

import amaral.pt.model.entity.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CrudService implements PanacheRepositoryBase<Resource, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public boolean AddResource(String apiKey, String resource, String requestBody) {
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

    public Map<String, Object> getSingleResource(String data_id, String topic){
        Resource resource = find("dataId = ?1 and topic = ?2", data_id, topic).firstResult();

        return resource.getData();
    }

    public List<Map<String, Object>> getAllResource(String apiKey, String topic){
        List<Resource> resources = find("apikey = ?1 and topic = ?2", apiKey, topic).list();

        return resources.stream()
                .map(Resource::getData)
                .collect(Collectors.toList());
    }

    public void deleteResource(String id, String topic) {
        delete("dataId = ?1 and topic = ?2", id, topic);
    }

    public String updateResource(String id, String topic, String data) throws JsonProcessingException {
        Resource resource = find("dataId = ?1 and topic = ?2", id, topic).firstResult();

        TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {};
        Map<String, Object> dataMap = mapper.readValue(data, mapType);

        dataMap.put("_id", id);

        resource.setData(dataMap);

        persist(resource);

        return id;
    }
}
