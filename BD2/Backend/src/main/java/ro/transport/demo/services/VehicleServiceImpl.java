package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.StatusDao;
import ro.transport.demo.dao.VehicleDao;
import ro.transport.demo.domain.Status;
import ro.transport.demo.domain.Vehicle;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private StatusDao statusDao;

    @Override
    public Vehicle add(Vehicle vehicle) {
        Status status = statusDao.findByName("Available");
        vehicle.setStatus(status);

        return vehicleDao.add(vehicle);
    }

    @Override
    public Vehicle find(Long id) {
        return vehicleDao.find(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    @Override
    public List<Vehicle> findByStatusName(String statusName) {
        if (statusName.equals("All")) {
            return findAll();
        }

        return vehicleDao.findByStatusName(statusName);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return vehicleDao.update(vehicle);
    }

    @Override
    public void delete(Long id) {
        vehicleDao.delete(id);
    }
}
