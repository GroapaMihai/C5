package ro.transport.demo.services;

import ro.transport.demo.domain.Status;
import ro.transport.demo.domain.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle add(Vehicle vehicle);

    Vehicle find(Long id);

    List<Vehicle> findAll();

    List<Vehicle> findByStatusName(String statusName);

    Vehicle update(Vehicle vehicle);

    void delete(Long id);
}
