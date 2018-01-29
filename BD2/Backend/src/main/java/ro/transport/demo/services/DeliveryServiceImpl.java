package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.DeliveryDao;
import ro.transport.demo.dao.StatusDao;
import ro.transport.demo.domain.Delivery;
import ro.transport.demo.domain.Status;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired
    private StatusDao statusDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Override
    public Delivery add(Delivery delivery) {
        Status status = statusDao.findByName("Available");
        delivery.setStatus(status);

        return deliveryDao.add(delivery);
    }

    @Override
    public Delivery find(Long id) {
        return deliveryDao.find(id);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryDao.findAll();
    }

    @Override
    public List<Delivery> findByStatusName(String statusName) {
        if (statusName.equals("All")) {
            return findAll();
        }

        return deliveryDao.findByStatusName(statusName);
    }

    @Override
    public Delivery update(Delivery delivery) {
        return deliveryDao.update(delivery);
    }

    @Override
    public void delete(Long id) {
        deliveryDao.delete(id);
    }
}
