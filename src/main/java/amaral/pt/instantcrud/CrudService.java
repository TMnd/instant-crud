package amaral.pt.instantcrud;

import amaral.pt.apimgr.ApiService;
import amaral.pt.instantcrud.model.entity.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CrudService implements PanacheRepositoryBase<Resource, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Inject
    ApiService apiService;

    private Boolean isAuthorized(String apiKey, String origin) {
        return this.apiService.authorize(apiKey, origin);
    }

    @Transactional
    public boolean AddResource(String apiKey, String resource, String requestBody, String origin) {

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return false;
        }

        try {
            String uuid = UUID.randomUUID().toString();

            TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
            Map<String, Object> bodyMap = mapper.readValue(requestBody, mapType);
            bodyMap.put("_id", uuid);

            Resource topic = new Resource();
            topic.setDataId(uuid);
            topic.setApikey(apiKey);
            topic.setTopic(resource);
            topic.setData(bodyMap);

            topic.persist();
        } catch (JsonProcessingException e) {
            System.out.println(e); //Todo add logger
            return false;
        }

        return true;
    }

    public Map<String, Object> getSingleResource(String apikey, String data_id, String topic, String origin){

        Boolean authorize = isAuthorized(apikey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return null;
        }

        Resource resource = find("dataId = ?1 and topic = ?2", data_id, topic).firstResult();

        return resource.getData();
    }

    public List<Map<String, Object>> getAllResource(String apiKey, String topic, String origin){

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return null;
        }

        List<Resource> resources = find("apikey = ?1 and topic = ?2", apiKey, topic).list();

        return resources.stream()
                .map(Resource::getData)
                .collect(Collectors.toList());
    }

    public Long deleteResource(String apiKey, String id, String topic, String origin) {

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return 0L;
        }

        delete("dataId = ?1 and topic = ?2", id, topic);
        return 1L;
    }

    public String updateResource(String apiKey, String id, String topic, String data, String origin) throws JsonProcessingException {

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return null;
        }

        Resource resource = find("dataId = ?1 and topic = ?2", id, topic).firstResult();

        TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
        Map<String, Object> dataMap = mapper.readValue(data, mapType);

        dataMap.put("_id", id);

        resource.setData(dataMap);

        persist(resource);

        return id;
    }
}
