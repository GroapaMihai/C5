package client_app.appl.domain.builders;

import client_app.appl.domain.entities.SeverityLevel;

public class SeverityLevelBuilder {

	private Integer id;
	private String severityLevel;
	
	public SeverityLevelBuilder() {
	    }
	
	public Integer getId() {
		return id;
	}

	public String getSeverityLevel() {
		return severityLevel;
	}

	public SeverityLevelBuilder withId(Integer id) {
        this.id = id;

        return this;
    }

    public SeverityLevelBuilder withSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;

        return this;
    }

    public SeverityLevel build() {
    	return new SeverityLevel(this);
    }
}
