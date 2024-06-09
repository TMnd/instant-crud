package amaral.pt.apimgr.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.util.List;

@Entity
@Table(name="api")
public class Api extends PanacheEntityBase {

	@Id
	private String apiKey;
	private String origin;

	public Api() {
		// Hibernate default constructor
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
