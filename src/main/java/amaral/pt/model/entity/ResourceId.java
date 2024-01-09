package amaral.pt.model.entity;


import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ResourceId implements Serializable {

    private String id;
    private String resource;

    public ResourceId() {
    }

    public ResourceId(String id, String resource) {
        this.id = id;
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
