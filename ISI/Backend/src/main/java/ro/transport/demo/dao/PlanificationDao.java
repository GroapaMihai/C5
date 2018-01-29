package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.LinkedCaseInsensitiveMap;
import ro.transport.demo.domain.Planification;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlanificationDao {
    @Autowired
    private Connection connection;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DeliveryDao deliveryDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private StatusDao statusDao;

    public Planification add(Planification planification) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call add_planification(?,?,?,?)}");
            callableStatement.setLong(1, planification.getDriver().getId());
            callableStatement.setLong(2, planification.getTruck().getId());
            callableStatement.setLong(3, planification.getDelivery().getId());
            callableStatement.setLong(4, planification.getStatus().getId());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return planification;
    }

    public Planification find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Planification planification = null;

        try {
            callableStatement = connection.prepareCall("{call get_planification_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                planification = new Planification();
                planification.setId(resultSet.getLong(1));
                planification.setDriver(employeeDao.find(resultSet.getLong(2)));
                planification.setTruck(vehicleDao.find(resultSet.getLong(3)));
                planification.setDelivery(deliveryDao.find(resultSet.getLong(4)));
                planification.setStatus(statusDao.find(resultSet.getLong(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return planification;
    }

    public Planification update(Planification planification) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call update_planification(?,?,?,?,?)}");
            callableStatement.setLong(1, planification.getId());
            callableStatement.setLong(2, planification.getDriver().getId());
            callableStatement.setLong(3, planification.getTruck().getId());
            callableStatement.setLong(4, planification.getDelivery().getId());
            callableStatement.setLong(5, planification.getStatus().getId());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return planification;
    }

    private List<Planification> extractPlanificationsFromResultSet(ResultSet resultSet) {
        List<Planification> planifications = new ArrayList<>();
        Planification planification;

        if (resultSet == null) {
            return planifications;
        }

        try {
            while (resultSet.next()) {
                planification = new Planification();
                planification.setId(resultSet.getLong(1));
                planification.setDriver(employeeDao.find(resultSet.getLong(2)));
                planification.setTruck(vehicleDao.find(resultSet.getLong(3)));
                planification.setDelivery(deliveryDao.find(resultSet.getLong(4)));
                planification.setStatus(statusDao.find(resultSet.getLong(5)));
                planifications.add(planification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return planifications;
    }

    public List<Planification> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_all_planifications()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractPlanificationsFromResultSet(resultSet);
    }

    public List<Planification> findByDriverId(Long driverId) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_planifications_by_employee_id(?)}");
            callableStatement.setLong(1, driverId);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractPlanificationsFromResultSet(resultSet);
    }

    public List<Planification> findByStatusName(String statusName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_planifications_by_status_name(?)}");
            callableStatement.setString(1, statusName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractPlanificationsFromResultSet(resultSet);
    }

    public List<Planification> findByDriverIdAndStatusName(Long driverId, String statusName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_planifications_by_employee_id_and_status_name(?,?)}");
            callableStatement.setLong(1, driverId);
            callableStatement.setString(2, statusName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractPlanificationsFromResultSet(resultSet);
    }

    public void delete(Long id) {

    }
}
