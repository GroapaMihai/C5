package ro.transport.demo.services;

import ro.transport.demo.domain.Address;

import java.util.List;

public interface AddressService {
    Address find(Long id);

    List<Address> findAll();

    List<Address> findByCountryName(String countryName);
}
