package ro.transport.demo.services;

import ro.transport.demo.domain.Delivery;
import ro.transport.demo.domain.Status;

import java.util.List;

public interface DeliveryService {
    Delivery add(Delivery delivery);

    Delivery find(Long id);

    List<Delivery> findAll();

    List<Delivery> findByStatusName(String statusName);

    Delivery update(Delivery delivery);

    void delete(Long id);
}
