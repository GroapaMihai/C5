package client_app.appl.domain.builders;

import java.sql.Timestamp;

import client_app.appl.domain.entities.WaterSensor;

public class WaterSensorBuilder {
    private Integer id;
    private Double latitude;
    private Double longitude;
    private Timestamp sampleRate;
    private Integer authorityId;
    
    public WaterSensorBuilder() {
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

	public Timestamp getSampleRate() {
		return sampleRate;
	}

	public Integer getAuthorityId() {
		return authorityId;
	}

	public WaterSensorBuilder withId(Integer id) {
        this.id = id;

        return this;
    }

    public WaterSensorBuilder withLatitude(Double latitude) {
        this.latitude = latitude;

        return this;
    }

    public WaterSensorBuilder withLongitude(Double longitude) {
        this.longitude = longitude;

        return this;
    }

    public WaterSensorBuilder withSampleRate(Timestamp sampleRate) {
        this.sampleRate = sampleRate;

        return this;
    }

	public WaterSensorBuilder withAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;

        return this;
    }

    public WaterSensor build() {
        return new WaterSensor(this);
    }
}
