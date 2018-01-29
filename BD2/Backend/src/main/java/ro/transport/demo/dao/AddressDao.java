package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ro.transport.demo.domain.Address;
import ro.transport.demo.domain.Country;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressDao {
    @Autowired
    private Connection connection;

    @Autowired
    private CountryDao countryDao;

    public Address find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Address address = null;

        try {
            callableStatement = connection.prepareCall("{call get_address_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                address = new Address();
                address.setId(resultSet.getLong(1));
                address.setCity(resultSet.getString(2));
                address.setCountry(countryDao.find(resultSet.getLong(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }

    private List<Address> extractAddressesFromResultSet(ResultSet resultSet) {
        List<Address> addresses = new ArrayList<>();
        Address address;

        try {
            while (resultSet.next()) {
                address = new Address();
                address.setId(resultSet.getLong(1));
                address.setCity(resultSet.getString(2));
                address.setCountry(countryDao.find(resultSet.getLong(3)));
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    public List<Address> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_all_addresses()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractAddressesFromResultSet(resultSet);
    }

    public List<Address> findByCountryName(String countryName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_addresses_by_country_name(?)}");
            callableStatement.setString(1, countryName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractAddressesFromResultSet(resultSet);
    }
}
