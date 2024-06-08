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
import org.jboss.logging.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@ApplicationScoped
public class CrudService implements PanacheRepositoryBase<Resource, String> {

    private static final Logger LOG = Logger.getLogger(CrudService.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String  DATA_TOPIC_QUERY  = "dataId = ?1 and topic = ?2";

    @Inject
    ApiService apiService;

    private Boolean isAuthorized(String apiKey, String origin) {
	    try {
		    return this.apiService.authorize(apiKey, origin);
	    } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
	    }
        return false;
    }

    @Transactional
    public boolean addResource(String apiKey, String resource, String requestBody, String origin) {

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
            LOG.error(e.getMessage());
            return false;
        }

        return true;
    }

    public Map<String, Object> getSingleResource(String apikey, String dataId, String topic, String origin){

        Boolean authorize = isAuthorized(apikey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return new HashMap<>();
        }

        Resource resource = find(DATA_TOPIC_QUERY,  dataId, topic).firstResult();

        return resource.getData();
    }

    public List<Map<String, Object>> getAllResource(String apiKey, String topic, String origin){

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return new ArrayList<>();
        }

        List<Resource> resources = find("apikey = ?1 and topic = ?2", apiKey, topic).list();

        return resources.stream()
                .map(Resource::getData)
                .toList();
    }

    public Long deleteResource(String apiKey, String id, String topic, String origin) {

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return 0L;
        }

        delete(DATA_TOPIC_QUERY, id, topic);
        return 1L;
    }

    public String updateResource(String apiKey, String id, String topic, String data, String origin) throws JsonProcessingException {

        Boolean authorize = isAuthorized(apiKey, origin);

        if(BooleanUtils.isFalse(authorize)) {
            return null;
        }

        Resource resource = find(DATA_TOPIC_QUERY, id, topic).firstResult();

        TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
        Map<String, Object> dataMap = mapper.readValue(data, mapType);

        dataMap.put("_id", id);

        resource.setData(dataMap);

        persist(resource);

        return id;
    }
}
