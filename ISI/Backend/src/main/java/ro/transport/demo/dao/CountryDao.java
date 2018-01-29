package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ro.transport.demo.domain.Country;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryDao {
    @Autowired
    private Connection connection;

    public Country find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Country country = null;

        try {
            callableStatement = connection.prepareCall("{call get_country_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getLong(1));
                country.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }

    public List<Country> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet;
        List<Country> countries = new ArrayList<>();
        Country country;

        try {
            callableStatement = connection.prepareCall("{call get_all_countries()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getLong(1));
                country.setName(resultSet.getString(2));
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }

    public Country findByName(String name) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Country country = null;

        try {
            callableStatement = connection.prepareCall("{call get_country_by_name(?)}");
            callableStatement.setString(1, name);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getLong(1));
                country.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }
}
