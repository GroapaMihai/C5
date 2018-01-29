package ro.transport.demo.services;

import ro.transport.demo.domain.Color;

import java.util.List;

public interface ColorService {
    Color find(Long id);

    List<Color> findAll();
}
