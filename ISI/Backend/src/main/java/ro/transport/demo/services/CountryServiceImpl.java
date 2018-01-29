package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import ro.transport.demo.dao.CountryDao;
import ro.transport.demo.domain.Country;

import java.util.List;

public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDao countryDao;

    @Override
    public Country find(Long id) {
        return countryDao.find(id);
    }

    @Override
    public List<Country> findAll() {
        return countryDao.findAll();
    }

    @Override
    public Country findByName(String name) {
        return countryDao.findByName(name);
    }
}
