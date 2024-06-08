package amaral.pt.instantcrud.model.entity;

import amaral.pt.instantcrud.model.convertions.JsonToMapConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="resource")
public class Resource extends PanacheEntityBase{

    @Id
    @Column(name="data_id")
    private String dataId;
    private String apikey;
    private String topic;
    @Convert(converter= JsonToMapConverter.class)
    private Map<String, Object> data;

    public Resource() {
        // Hibernate default constructor
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
