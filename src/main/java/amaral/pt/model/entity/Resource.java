package amaral.pt.model.entity;
import jakarta.json.Json;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Map;

@Entity
@Table(name="resource")
public class Resource {
    @EmbeddedId
    private ResourceId id;
    @Column(columnDefinition = "jsonb", nullable = false)
    private Json data;

    public Resource() {
    }

    public Resource(ResourceId id, Json data) {
        this.id = id;
        this.data = data;
    }

    public ResourceId getId() {
        return id;
    }

    public void setId(ResourceId id) {
        this.id = id;
    }

    public Json getData() {
        return data;
    }

    public void setData(Json data) {
        this.data = data;
    }
}
