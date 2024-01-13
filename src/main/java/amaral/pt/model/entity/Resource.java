package amaral.pt.model.entity;
import amaral.pt.model.convertions.JsonToMapConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="resource")
public class Resource extends PanacheEntityBase {

    @Id
    @Column(name="data_id")
    private String dataId;
    private String apikey;
    private String resource;
    @Convert(converter= JsonToMapConverter.class)
    private Map<String, Object> data;

    public Resource() {}

    public Resource(String dataId, String apikey, String resource, Map<String, Object> data) {
        this.dataId = dataId;
        this.apikey = apikey;
        this.resource = resource;
        this.data = data;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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
