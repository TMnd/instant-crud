package amaral.pt.model.entity;


import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ResourceId implements Serializable {

    private String apikey;
    private String resource;

    public ResourceId() {
    }

    public ResourceId(String apikey, String resource) {
        this.apikey = apikey;
        this.resource = resource;
    }

    public String getId() {
        return apikey;
    }

    public void setId(String apikey) {
        this.apikey = apikey;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
