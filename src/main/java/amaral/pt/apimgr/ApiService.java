package amaral.pt.apimgr;

import amaral.pt.apimgr.model.entity.Api;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@ApplicationScoped
public class ApiService {

    private final ObjectMapper mapper = new ObjectMapper();

    private String generateApiKey(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    private void saveGeneratedApi(String apiKey, String origin) {
        Api api = new Api();
        api.setApiKey(apiKey);
        api.setOrigin(origin);

        api.persist();
    }

    public String createApiKey(String requestBody) throws JsonProcessingException, NoSuchAlgorithmException {
        TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
        Map<String, Object> bodyMap = mapper.readValue(requestBody, mapType);

        String origin = String.valueOf(bodyMap.get("origin"));
        String apiKey = generateApiKey(origin);

        saveGeneratedApi(apiKey, origin);

        return apiKey;
    }

    public boolean authorize(String apikey, String origin) throws NoSuchAlgorithmException {
        String challengeKey = generateApiKey(origin);
        return StringUtils.equals(challengeKey, apikey);
    }
}
