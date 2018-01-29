package client_app.appl.domain.entities;

import java.sql.Timestamp;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.IncidentBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".INCIDENTS")
public class Incident {
    @Id(name = "ID")
    private Integer id;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "REPORTING_DATE")
    private Timestamp reportingDate;

    @Column(name = "AFFECTED_SURFACE")
    private Double affectedSurface;

    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "FK_PICTURE")
    private Integer pictureId;

    @Column(name = "FK_INCIDENT_TYPE")
    private Integer incidentTypeId;
    
    @Column(name = "FK_SEVERITY_LEVEL")
    private Integer severityLevelId;
    
    @Column(name = "FK_VOLUNTEER")
    private Integer volunteerId;

    public Incident() {
    }

    public Incident(IncidentBuilder incidentBuilder) {
        id = incidentBuilder.getId();
        latitude = incidentBuilder.getLatitude();
        longitude = incidentBuilder.getLongitude();
        reportingDate = incidentBuilder.getReportingDate();
        affectedSurface = incidentBuilder.getAffectedSurface();
        description = incidentBuilder.getDescription();
        pictureId = incidentBuilder.getPictureId();
        incidentTypeId = incidentBuilder.getIncidentTypeId();
        severityLevelId = incidentBuilder.getSeverityLevelId();
        volunteerId = incidentBuilder.getVolunteerId();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Timestamp getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(Timestamp reportingDate) {
		this.reportingDate = reportingDate;
	}

	public Double getAffectedSurface() {
		return affectedSurface;
	}

	public void setAffectedSurface(Double affectedSurface) {
		this.affectedSurface = affectedSurface;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCapture() {
		return pictureId;
	}

	public void setCapture(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public Integer getIncidentTypeId() {
		return incidentTypeId;
	}

	public void setIncidentTypeId(Integer incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}

	public Integer getSeverityLevelId() {
		return severityLevelId;
	}

	public void setSeverityLevelId(Integer severityLevelId) {
		this.severityLevelId = severityLevelId;
	}

	public Integer getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(Integer volunteerId) {
		this.volunteerId = volunteerId;
	}

	@Override
	public String toString() {
		return "Incident [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", reportingDate="
				+ reportingDate + ", affectedSurface=" + affectedSurface + ", description=" + description
				+ ", pictureId=" + pictureId + ", incidentTypeId=" + incidentTypeId + ", severityLevelId="
				+ severityLevelId + ", volunteerId=" + volunteerId + "]";
	}
}
