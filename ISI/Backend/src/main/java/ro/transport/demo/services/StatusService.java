package ro.transport.demo.services;

import ro.transport.demo.domain.Status;

import java.util.List;

public interface StatusService {
    Status find(Long id);

    List<Status> findAll();

    Status findByName(String name);
}
