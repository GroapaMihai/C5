package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.*;
import ro.transport.demo.domain.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class PlanificationServiceImpl implements PlanificationService {
    @Autowired
    private StatusDao statusDao;

    @Autowired
    private PlanificationDao planificationDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Planification add(Planification planification) {
        Status status = statusDao.findByName("In transit");
        Delivery delivery = planification.getDelivery();
        Vehicle vehicle = planification.getTruck();
        Employee employee = planification.getDriver();

        delivery.setStatus(status);
        vehicle.setStatus(status);
        employee.setStatus(status);
        planification.setStatus(status);

        delivery = deliveryDao.update(delivery);
        vehicle = vehicleDao.update(vehicle);
        employee = employeeDao.update(employee);

        planification.setDelivery(delivery);
        planification.setTruck(vehicle);
        planification.setDriver(employee);

        return planificationDao.add(planification);
    }

    @Override
    public Planification find(Long id) {
        return planificationDao.find(id);
    }

    @Override
    public List<Planification> findAll() {
        return planificationDao.findAll();
    }

    @Override
    public List<Planification> findByDriverId(Long driverId) {
        return planificationDao.findByDriverId(driverId);
    }

    @Override
    public List<Planification> findByStatusName(String statusName) {
        if (statusName.equals("All")) {
            return findAll();
        }

        return planificationDao.findByStatusName(statusName);
    }

    @Override
    public List<Planification> findByDriverIdAndStatusName(Long driverId, String statusName) {
        if (statusName.equals("All")) {
            return findByDriverId(driverId);
        }

        return planificationDao.findByDriverIdAndStatusName(driverId, statusName);
    }

    @Override
    public Planification update(Planification planification) {
        return planificationDao.update(planification);
    }

    @Override
    public void delete(Long id) {
        planificationDao.delete(id);
    }

    @Override
    public Planification deliver(Planification planification) {
        Status deliveredStatus = statusDao.findByName("Delivered");
        Status availableStatus = statusDao.findByName("Available");

        Vehicle vehicle = planification.getTruck();
        Employee employee = planification.getDriver();
        Delivery delivery = planification.getDelivery();

        vehicle.setStatus(availableStatus);
        employee.setStatus(availableStatus);
        delivery.setStatus(deliveredStatus);
        delivery.setDeliveryDate(new Date(System.currentTimeMillis()));
        planification.setStatus(deliveredStatus);

        vehicle = vehicleDao.update(vehicle);
        employee = employeeDao.update(employee);
        delivery = deliveryDao.update(delivery);

        planification.setTruck(vehicle);
        planification.setDriver(employee);
        planification.setDelivery(delivery);

        return planificationDao.update(planification);
    }
}
