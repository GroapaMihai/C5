package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;
import ro.transport.demo.domain.Address;
import ro.transport.demo.services.AddressService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/address/id/{id}")
    @ResponseBody
    public Address find(@PathVariable("id") Long id) {
        return addressService.find(id);
    }

    @GetMapping("/address/all")
    @ResponseBody
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/address/country/{countryName}")
    @ResponseBody
    public List<Address> findByCountryName(@PathVariable("countryName") String countryName) {
        return addressService.findByCountryName(countryName);
    }
}
