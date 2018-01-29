package ro.transport.demo.domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Delivery {
    private Long id;
    private Address startingAddress;
    private Address destinationAddress;
    private String cargo;
    private BigDecimal cargoWeight;
    private Priority priority;
    private Integer companyReward;
    private Integer driverReward;
    private Status status;
    private Date deliveryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(Address startingAddress) {
        this.startingAddress = startingAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(BigDecimal cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Integer getCompanyReward() {
        return companyReward;
    }

    public void setCompanyReward(Integer companyReward) {
        this.companyReward = companyReward;
    }

    public Integer getDriverReward() {
        return driverReward;
    }

    public void setDriverReward(Integer driverReward) {
        this.driverReward = driverReward;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (!id.equals(delivery.id)) return false;
        if (!startingAddress.equals(delivery.startingAddress)) return false;
        if (!destinationAddress.equals(delivery.destinationAddress)) return false;
        if (!cargo.equals(delivery.cargo)) return false;
        if (cargoWeight != null ? !cargoWeight.equals(delivery.cargoWeight) : delivery.cargoWeight != null)
            return false;
        if (!priority.equals(delivery.priority)) return false;
        if (!companyReward.equals(delivery.companyReward)) return false;
        if (!driverReward.equals(delivery.driverReward)) return false;
        if (deliveryDate != null ? !deliveryDate.equals(delivery.deliveryDate) : delivery.deliveryDate != null)
            return false;
        return status.equals(delivery.status);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + startingAddress.hashCode();
        result = 31 * result + destinationAddress.hashCode();
        result = 31 * result + cargo.hashCode();
        result = 31 * result + (cargoWeight != null ? cargoWeight.hashCode() : 0);
        result = 31 * result + priority.hashCode();
        result = 31 * result + companyReward.hashCode();
        result = 31 * result + driverReward.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", startingAddress=" + startingAddress +
                ", destinationAddress=" + destinationAddress +
                ", cargo='" + cargo + '\'' +
                ", cargoWeight=" + cargoWeight +
                ", priority=" + priority +
                ", companyReward=" + companyReward +
                ", driverReward=" + driverReward +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
