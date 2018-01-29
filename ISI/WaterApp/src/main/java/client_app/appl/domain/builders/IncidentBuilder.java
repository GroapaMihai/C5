package client_app.appl.domain.builders;

import java.sql.Timestamp;

import client_app.appl.domain.entities.Incident;

public class IncidentBuilder {
	
    private Integer id;
    private Double latitude;
    private Double longitude;
    private Timestamp reportingDate;
    private Double affectedSurface;
    private String description;
    private Integer pictureId;
    private Integer incidentTypeId;
    private Integer severityLevelId;
    private Integer volunteerId;

    public IncidentBuilder() {
    }

    public Integer getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
		return longitude;
	}

	public Timestamp getReportingDate() {
		return reportingDate;
	}

	public Double getAffectedSurface() {
		return affectedSurface;
	}

	public String getDescription() {
		return description;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public Integer getIncidentTypeId() {
		return incidentTypeId;
	}

	public Integer getSeverityLevelId() {
		return severityLevelId;
	}

	public Integer getVolunteerId() {
		return volunteerId;
	}

	public IncidentBuilder withId(Integer id) {
        this.id = id;

        return this;
    }

    public IncidentBuilder withLatitude(Double latitude) {
        this.latitude = latitude;

        return this;
    }

    public IncidentBuilder withLongitude(Double longitude) {
        this.longitude = longitude;

        return this;
    }

    public IncidentBuilder withReportingDate(Timestamp reportingDate) {
        this.reportingDate = reportingDate;

        return this;
    }
    
    public IncidentBuilder withAffectedSurface(Double affectedSurface) {
        this.affectedSurface = affectedSurface;

        return this;
    }
    
    public IncidentBuilder withDescription(String description) {
        this.description = description;

        return this;
    }
    
    public IncidentBuilder withPictureId(Integer pictureId) {
        this.pictureId = pictureId;

        return this;
    }
    
    public IncidentBuilder withIncidentTypeId(Integer incidentTypeId) {
        this.incidentTypeId = incidentTypeId;

        return this;
    }
    	
    public IncidentBuilder withSeverityLevelId(Integer severityLevelId) {
    	this.severityLevelId = severityLevelId;
    	
    	return this;
    }
    
    public IncidentBuilder withVolunteerId(Integer volunteerId) {
    	this.volunteerId = volunteerId;
    	
    	return this;
    }
    
    public Incident build() {
        return new Incident(this);
    }
}
