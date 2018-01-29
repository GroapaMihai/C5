package ro.transport.demo.services;

import ro.transport.demo.domain.Priority;

import java.util.List;

public interface PriorityService {
    Priority find(Long id);

    List<Priority> findAll();

    Priority findByName(String name);
}
