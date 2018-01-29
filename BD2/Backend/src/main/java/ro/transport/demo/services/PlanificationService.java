package ro.transport.demo.services;

import ro.transport.demo.domain.Employee;
import ro.transport.demo.domain.Planification;
import ro.transport.demo.domain.Status;
import ro.transport.demo.domain.Vehicle;

import java.util.List;

public interface PlanificationService {
    Planification add(Planification planification);

    Planification find(Long id);

    List<Planification> findAll();

    List<Planification> findByDriverId(Long driverId);

    List<Planification> findByStatusName(String statusName);

    List<Planification> findByDriverIdAndStatusName(Long driverId, String statusName);

    Planification update(Planification planification);

    void delete(Long id);

    Planification deliver(Planification planification);
}
