package client_app.appl.domain.builders;

import java.sql.Timestamp;

import client_app.appl.domain.entities.Sample;

public class SampleBuilder {
	private Integer id;
	private Integer turbidity;
	private Double ph;
	private Integer salinity;
	private Double dissolvedOxygen;
	private Integer EColi;
	private Integer enterococcusBacteria;
	private Timestamp sampleTime;
    private Integer waterSensorId;
    
    public SampleBuilder() {
    }

	public Integer getId() {
		return id;
	}

	public Integer getTurbidity() {
		return turbidity;
	}

	public Double getPh() {
		return ph;
	}

	public Integer getSalinity() {
		return salinity;
	}

	public Double getDissolvedOxygen() {
		return dissolvedOxygen;
	}

	public Integer getEColi() {
		return EColi;
	}

	public Integer getEnterococcusBacteria() {
		return enterococcusBacteria;
	}

	public Timestamp getSampleTime() {
		return sampleTime;
	}

	public Integer getWaterSensorId() {
		return waterSensorId;
	}
    
	public SampleBuilder withId(Integer id) {
        this.id = id;

        return this;
    }
	
	public SampleBuilder withTurbidity(Integer turbidity) {
		this.turbidity = turbidity;

		return this;
	}
	
	public SampleBuilder withPh(Double ph) {
		this.ph = ph;
	
		return this;
	}
	
	public SampleBuilder withSalinity(Integer salinity) {
	    this.salinity = salinity;
	
	    return this;
	}
	
	public SampleBuilder withDissolvedOxygen(Double dissolvedOxygen) {
		this.dissolvedOxygen = dissolvedOxygen;
		
		return this;
	}
	
	public SampleBuilder withEColi(Integer EColi) {
		this.EColi = EColi;
		
		return this;
	}
	
	public SampleBuilder withEnterococcusBacteria(Integer enterococcusBacteria) {
		this.enterococcusBacteria = enterococcusBacteria;
		
		return this;
	}
	
	public SampleBuilder withSampleTime(Timestamp sampleTime) {
	    this.sampleTime = sampleTime;
	
	    return this;
	}
	
	public SampleBuilder withWaterSensorId(Integer waterSensorId) {
		this.waterSensorId = waterSensorId;
		
		return this;
	}
	
	public Sample build() {
		return new Sample(this);
	}
}
