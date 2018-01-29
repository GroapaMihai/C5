package ro.transport.demo.services;

import ro.transport.demo.domain.Country;

import java.util.List;

public interface CountryService {
    Country find(Long id);

    List<Country> findAll();

    Country findByName(String name);
}
