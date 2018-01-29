package ro.transport.demo.domain;

public class Planification {
    private Long id;
    private Employee driver;
    private Vehicle truck;
    private Delivery delivery;
    private Status status;

    public Planification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    public Vehicle getTruck() {
        return truck;
    }

    public void setTruck(Vehicle truck) {
        this.truck = truck;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
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

        Planification that = (Planification) o;

        if (!id.equals(that.id)) return false;
        if (!driver.equals(that.driver)) return false;
        if (!truck.equals(that.truck)) return false;
        if (!delivery.equals(that.delivery)) return false;
        return status.equals(that.status);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + driver.hashCode();
        result = 31 * result + truck.hashCode();
        result = 31 * result + delivery.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Planification{" +
                "id=" + id +
                ", driver=" + driver +
                ", truck=" + truck +
                ", delivery=" + delivery +
                ", status=" + status +
                '}';
    }
}
