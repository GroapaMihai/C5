package client_app.appl.domain.builders;

import client_app.appl.domain.entities.IncidentType;

public class IncidentTypeBuilder {
	private Integer id;
	private String type;

	public IncidentTypeBuilder() {
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public IncidentTypeBuilder withId(Integer id) {
		this.id = id;
		
		return this;
	}

	public IncidentTypeBuilder withType(String type) {
		this.type = type;
		
		return this;
	}
	
	public IncidentType build() {
		return new IncidentType(this);
	}
}
