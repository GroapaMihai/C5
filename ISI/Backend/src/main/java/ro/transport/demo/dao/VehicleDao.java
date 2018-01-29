package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.LinkedCaseInsensitiveMap;
import ro.transport.demo.domain.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VehicleDao {
    @Autowired
    private Connection connection;

    @Autowired
    private ColorDao colorDao;

    @Autowired
    private StatusDao statusDao;

    public Vehicle add(Vehicle vehicle) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call add_vehicle(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, vehicle.getRegistrationNumber());
            callableStatement.setString(2, vehicle.getBrand());
            callableStatement.setString(3, vehicle.getModel());
            callableStatement.setLong(4, vehicle.getColor().getId());
            callableStatement.setDate(5, vehicle.getFirstRegistrationDate());
            callableStatement.setInt(6, vehicle.getTravelledDistance());
            callableStatement.setInt(7, vehicle.getCurrentValue());
            callableStatement.setLong(8, vehicle.getStatus().getId());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    public Vehicle find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Vehicle vehicle = null;

        try {
            callableStatement = connection.prepareCall("{call get_vehicle_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                vehicle = new Vehicle();
                vehicle.setId(resultSet.getLong(1));
                vehicle.setRegistrationNumber(resultSet.getString(2));
                vehicle.setBrand(resultSet.getString(3));
                vehicle.setModel(resultSet.getString(4));
                vehicle.setColor(colorDao.find(resultSet.getLong(5)));
                vehicle.setFirstRegistrationDate(resultSet.getDate(6));
                vehicle.setTravelledDistance(resultSet.getInt(7));
                vehicle.setCurrentValue(resultSet.getInt(8));
                vehicle.setStatus(statusDao.find(resultSet.getLong(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    public Vehicle update(Vehicle vehicle) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call update_vehicle(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setLong(1, vehicle.getId());
            callableStatement.setString(2, vehicle.getRegistrationNumber());
            callableStatement.setString(3, vehicle.getBrand());
            callableStatement.setString(4, vehicle.getModel());
            callableStatement.setLong(5, vehicle.getColor().getId());
            callableStatement.setDate(6, vehicle.getFirstRegistrationDate());
            callableStatement.setInt(7, vehicle.getTravelledDistance());
            callableStatement.setInt(8, vehicle.getCurrentValue());
            callableStatement.setLong(9, vehicle.getStatus().getId());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    private List<Vehicle> extractVehiclesFromResultSet(ResultSet resultSet) {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle;

        try {
            while (resultSet.next()) {
                vehicle = new Vehicle();
                vehicle.setId(resultSet.getLong(1));
                vehicle.setRegistrationNumber(resultSet.getString(2));
                vehicle.setBrand(resultSet.getString(3));
                vehicle.setModel(resultSet.getString(4));
                vehicle.setColor(colorDao.find(resultSet.getLong(5)));
                vehicle.setFirstRegistrationDate(resultSet.getDate(6));
                vehicle.setTravelledDistance(resultSet.getInt(7));
                vehicle.setCurrentValue(resultSet.getInt(8));
                vehicle.setStatus(statusDao.find(resultSet.getLong(9)));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_all_vehicles()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractVehiclesFromResultSet(resultSet);
    }

    public List<Vehicle> findByStatusName(String statusName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_vehicles_by_status_name(?)}");
            callableStatement.setString(1, statusName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractVehiclesFromResultSet(resultSet);
    }

    public void delete(Long id) {

    }
}
