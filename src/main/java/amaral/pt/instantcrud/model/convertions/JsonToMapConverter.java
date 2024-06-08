package amaral.pt.instantcrud.model.convertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter
public class JsonToMapConverter implements AttributeConverter<Map<String,Object>, String> {

    private static final Logger LOG = Logger.getLogger(JsonToMapConverter.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> entity) {
        String columnData = "";

        try {
            columnData = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to convert Map to JSON: " + e.getMessage());
        }

        return columnData;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        Map<String, Object> entity = new HashMap<>();

        if (dbData != null) {
            try {
                entity = mapper.readValue(dbData, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                LOG.error("Failed to convert JSON to Map: " + e.getMessage());
            }
        }

        return entity;
    }
}
