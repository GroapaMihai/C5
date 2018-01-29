package ro.transport.demo.dao;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.LinkedCaseInsensitiveMap;
import ro.transport.demo.domain.Delivery;
import ro.transport.demo.domain.Priority;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeliveryDao {
    @Autowired
    private Connection connection;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private PriorityDao priorityDao;

    @Autowired
    private StatusDao statusDao;

    public Delivery add(Delivery delivery) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call add_delivery(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setLong(1, delivery.getStartingAddress().getId());
            callableStatement.setLong(2, delivery.getDestinationAddress().getId());
            callableStatement.setString(3, delivery.getCargo());
            callableStatement.setBigDecimal(4, delivery.getCargoWeight());
            callableStatement.setLong(5, delivery.getPriority().getId());
            callableStatement.setInt(6, delivery.getCompanyReward());
            callableStatement.setInt(7, delivery.getDriverReward());
            callableStatement.setLong(8, delivery.getStatus().getId());
            callableStatement.setDate(9, delivery.getDeliveryDate());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return delivery;
    }

    public Delivery find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Delivery delivery = null;

        try {
            callableStatement = connection.prepareCall("{call get_delivery_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                delivery = new Delivery();
                delivery.setId(resultSet.getLong(1));
                delivery.setStartingAddress(addressDao.find(resultSet.getLong(2)));
                delivery.setDestinationAddress(addressDao.find(resultSet.getLong(3)));
                delivery.setCargo(resultSet.getString(4));
                delivery.setCargoWeight(resultSet.getBigDecimal(5));
                delivery.setPriority(priorityDao.find(resultSet.getLong(6)));
                delivery.setCompanyReward(resultSet.getInt(7));
                delivery.setDriverReward(resultSet.getInt(8));
                delivery.setStatus(statusDao.find(resultSet.getLong(9)));
                delivery.setDeliveryDate(resultSet.getDate(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return delivery;
    }

    public Delivery update(Delivery delivery) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call update_delivery(?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setLong(1, delivery.getId());
            callableStatement.setLong(2, delivery.getStartingAddress().getId());
            callableStatement.setLong(3, delivery.getDestinationAddress().getId());
            callableStatement.setString(4, delivery.getCargo());
            callableStatement.setBigDecimal(5, delivery.getCargoWeight());
            callableStatement.setLong(6, delivery.getPriority().getId());
            callableStatement.setInt(7, delivery.getCompanyReward());
            callableStatement.setInt(8, delivery.getDriverReward());
            callableStatement.setLong(9, delivery.getStatus().getId());
            callableStatement.setDate(10, delivery.getDeliveryDate());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return delivery;
    }

    private List<Delivery> extractDeliveriesFromResultSet(ResultSet resultSet) {
        List<Delivery> deliveries = new ArrayList<>();
        Delivery delivery;

        if (resultSet == null) {
            return deliveries;
        }

        try {
            while (resultSet.next()) {
                delivery = new Delivery();
                delivery.setId(resultSet.getLong(1));
                delivery.setStartingAddress(addressDao.find(resultSet.getLong(2)));
                delivery.setDestinationAddress(addressDao.find(resultSet.getLong(3)));
                delivery.setCargo(resultSet.getString(4));
                delivery.setCargoWeight(resultSet.getBigDecimal(5));
                delivery.setPriority(priorityDao.find(resultSet.getLong(6)));
                delivery.setCompanyReward(resultSet.getInt(7));
                delivery.setDriverReward(resultSet.getInt(8));
                delivery.setStatus(statusDao.find(resultSet.getLong(9)));
                delivery.setDeliveryDate(resultSet.getDate(10));
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deliveries;
    }

    public List<Delivery> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_all_deliveries()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractDeliveriesFromResultSet(resultSet);
    }

    public List<Delivery> findByStatusName(String statusName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_deliveries_by_status_name(?)}");
            callableStatement.setString(1, statusName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractDeliveriesFromResultSet(resultSet);
    }

    public void delete(Long id) {

    }
}
