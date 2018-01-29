package client_app.appl.domain.entities;

import java.sql.Timestamp;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.WaterSensorBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".WATER_SENSORS")
public class WaterSensor {
    @Id(name = "ID")
    private Integer id;
    
    @Column(name = "LATITUDE")
    private Double latitude;
    
    @Column(name = "LONGITUDE")
    private Double longitude;
 
    @Column(name = "SAMPLE_RATE")
    private Timestamp sampleRate;
    
    @Column(name = "FK_AUTHORITY")
    private Integer authorityId;
    
    public WaterSensor() {
    }
    
    public WaterSensor(WaterSensorBuilder waterSensorBuilder) {
    	this.id = waterSensorBuilder.getId();
    	this.latitude = waterSensorBuilder.getLatitude();
    	this.longitude = waterSensorBuilder.getLongitude();
    	this.sampleRate = waterSensorBuilder.getSampleRate();
    	this.authorityId = waterSensorBuilder.getAuthorityId();
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

	public Timestamp getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(Timestamp sampleRate) {
		this.sampleRate = sampleRate;
	}

	public Integer getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	@Override
	public String toString() {
		return "WaterSensor [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", sampleRate="
				+ sampleRate + ", authorityId=" + authorityId + "]";
	}
}
