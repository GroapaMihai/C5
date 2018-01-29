package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.transport.demo.domain.Country;
import ro.transport.demo.services.CountryService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/country/id/{id}")
    @ResponseBody
    public Country find(@PathVariable("id") Long id) {
        return countryService.find(id);
    }

    @GetMapping("/country/all")
    @ResponseBody
    public List<Country> findAll() {
        return countryService.findAll();
    }
}
