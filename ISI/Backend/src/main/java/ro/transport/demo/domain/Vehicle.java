package ro.transport.demo.domain;

import java.sql.Date;

public class Vehicle {
    private Long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private Color color;
    private Date firstRegistrationDate;
    private Integer travelledDistance;
    private Status status;
    private Integer currentValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getFirstRegistrationDate() {
        return firstRegistrationDate;
    }

    public void setFirstRegistrationDate(Date firstRegistrationDate) {
        this.firstRegistrationDate = firstRegistrationDate;
    }

    public Integer getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Integer travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (!id.equals(vehicle.id)) return false;
        if (!registrationNumber.equals(vehicle.registrationNumber)) return false;
        if (!brand.equals(vehicle.brand)) return false;
        if (!model.equals(vehicle.model)) return false;
        if (!color.equals(vehicle.color)) return false;
        if (!firstRegistrationDate.equals(vehicle.firstRegistrationDate)) return false;
        if (!travelledDistance.equals(vehicle.travelledDistance)) return false;
        if (!status.equals(vehicle.status)) return false;
        return currentValue != null ? currentValue.equals(vehicle.currentValue) : vehicle.currentValue == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + registrationNumber.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + firstRegistrationDate.hashCode();
        result = 31 * result + travelledDistance.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (currentValue != null ? currentValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", firstRegistrationDate=" + firstRegistrationDate +
                ", travelledDistance=" + travelledDistance +
                ", status=" + status +
                ", currentValue=" + currentValue +
                '}';
    }
}
