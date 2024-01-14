package amaral.pt.apimgr;

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
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public String createApiKey(String requestBody) {
        String api = "";

        try {
            TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
            Map<String, Object> bodyMap = mapper.readValue(requestBody, mapType);
            String origin = String.valueOf(bodyMap.get("origin"));

            return generateApiKey(origin);

        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            System.out.println(e); //todo
        }

        return api;
    }

    public boolean authorize(String apikey, String origin) {
        String challengeKey = null;
        try {
            challengeKey = generateApiKey(origin);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e); //todo
            return false;
        }
        return StringUtils.equals(challengeKey, apikey);
    }
}
