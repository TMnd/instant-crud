package amaral.pt.model.entity;
import amaral.pt.model.convertions.JsonToMapConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="resource")
public class Resource extends PanacheEntityBase {
    @EmbeddedId
    private ResourceId resourceId;

    @Column(name="data_id")
    private String dataId;

    @Convert(converter= JsonToMapConverter.class)
    private Map<String, Object> data;

    public Resource() {}

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void setResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
