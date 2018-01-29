package client_app.appl.domain.entities;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.SeverityLevelBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".SEVERITY_LEVELS")
public class SeverityLevel {
	@Id(name = "ID")
	private Integer id;
	
	@Column(name = "SEVERITY_LEVEL")
	private String severityLevel;

	public SeverityLevel() {
	}

	public SeverityLevel(SeverityLevelBuilder severityLevelBuilder) {
		id = severityLevelBuilder.getId();
		severityLevel = severityLevelBuilder.getSeverityLevel();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(String severityLevel) {
		this.severityLevel = severityLevel;
	}


	@Override
	public String toString() {
		return "SeveritLevel [id=" + id + ", severityLevel=" + severityLevel + "]";
	}
}
