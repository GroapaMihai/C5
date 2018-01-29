package client_app.appl.domain.entities;

import java.sql.Timestamp;

import client_app.api.annotations.Column;
import client_app.api.annotations.Digits;
import client_app.api.annotations.Id;
import client_app.api.annotations.Range;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.SampleBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".SAMPLES")
public class Sample {
    @Id(name = "ID")
    private Integer id;

    @Column(name = "TURBIDITY")
    @Range(normal_min = 5, normal_max = 50, min = 0, max = 80)
    @Digits(integers = 2, decimals = 0)
    private Integer turbidity;
    
    @Column(name = "PH")
    @Range(normal_min = 4, normal_max = 10, min = 0, max = 14)
    @Digits(integers = 2, decimals = 2)
    private Double ph;
    
    @Column(name = "SALINITY")
    @Range(normal_min = 250, normal_max = 1000, min = 100, max = 1500)
    @Digits(integers = 4, decimals = 0)
    private Integer salinity;
    
    @Column(name = "DISSOLVED_OXYGEN")
    @Range(normal_min = 3, normal_max = 7, min = 0, max = 10)
    @Digits(integers = 1, decimals = 2)
    private Double dissolvedOxygen;
    
    @Column(name = "E_COLI")
    @Range(normal_min = 20, normal_max = 80, min = 0, max = 150)
    @Digits(integers = 3, decimals = 0)
    private Integer EColi;

    @Column(name = "ENTEROCOCCUS_BACTERIA")
    @Range(normal_min = 10, normal_max = 75, min = 0, max = 150)
    @Digits(integers = 3, decimals = 0)
    private Integer enterococcusBacteria;
    
    @Column(name = "SAMPLE_TIME")
    private Timestamp sampleTime;
    
    @Column(name = "FK_WATER_SENSOR")
    private Integer waterSensorId;
    
    public Sample() {
    }

    public Sample(SampleBuilder builder) {
    	this.id = builder.getId();
    	this.turbidity = builder.getTurbidity();
    	this.ph = builder.getPh();
    	this.salinity = builder.getSalinity();
    	this.dissolvedOxygen = builder.getDissolvedOxygen();
    	this.EColi = builder.getEColi();
    	this.enterococcusBacteria = builder.getEnterococcusBacteria();
    	this.sampleTime = builder.getSampleTime();
    	this.waterSensorId = builder.getWaterSensorId();
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTurbidity() {
		return turbidity;
	}

	public void setTurbidity(Integer turbidity) {
		this.turbidity = turbidity;
	}

	public Double getPh() {
		return ph;
	}

	public void setPh(Double ph) {
		this.ph = ph;
	}

	public Integer getSalinity() {
		return salinity;
	}

	public void setSalinity(Integer salinity) {
		this.salinity = salinity;
	}

	public Double getDissolvedOxygen() {
		return dissolvedOxygen;
	}

	public void setDissolvedOxygen(Double dissolvedOxygen) {
		this.dissolvedOxygen = dissolvedOxygen;
	}

	public Integer getEColi() {
		return EColi;
	}

	public void setEColi(Integer eColi) {
		EColi = eColi;
	}

	public Integer getEnterococcusBacteria() {
		return enterococcusBacteria;
	}

	public void setEnterococcusBacteria(Integer enterococcusBacteria) {
		this.enterococcusBacteria = enterococcusBacteria;
	}

	public Timestamp getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(Timestamp sampleTime) {
		this.sampleTime = sampleTime;
	}

	public Integer getWaterSensorId() {
		return waterSensorId;
	}

	public void setWaterSensorId(Integer waterSensorId) {
		this.waterSensorId = waterSensorId;
	}

	@Override
	public String toString() {
		return "Sample [id=" + id + ", turbidity=" + turbidity + ", ph=" + ph + ", salinity=" + salinity
				+ ", dissolvedOxygen=" + dissolvedOxygen + ", EColi=" + EColi + ", enterococcusBacteria="
				+ enterococcusBacteria + ", sampleTime=" + sampleTime + ", waterSensorId=" + waterSensorId + "]";
	}
}