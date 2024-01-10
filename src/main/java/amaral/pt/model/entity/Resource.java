package amaral.pt.model.entity;
import amaral.pt.model.convertions.JsonToMapConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="resource")
public class Resource extends PanacheEntityBase {
    @EmbeddedId
    private ResourceId id;

    @Convert(converter= JsonToMapConverter.class)
    private Map<String, Object> data;

    public Resource() {
    }

    public Resource(ResourceId id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    public ResourceId getId() {
        return id;
    }

    public void setId(ResourceId id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
