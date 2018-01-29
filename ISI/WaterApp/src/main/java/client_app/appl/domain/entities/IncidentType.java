package client_app.appl.domain.entities;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.IncidentTypeBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".INCIDENT_TYPES")
public class IncidentType {
	@Id(name = "ID")
	private Integer id;

	@Column(name = "TYPE")
	private String type;

	public IncidentType() {
	}

	public IncidentType(IncidentTypeBuilder builder) {
		this.id = builder.getId();
		this.type = builder.getType();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "IncidentType [id=" + id + ", type=" + type + "]";
	}
}
