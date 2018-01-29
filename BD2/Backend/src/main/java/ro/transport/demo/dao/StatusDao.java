package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ro.transport.demo.domain.Status;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatusDao {
    @Autowired
    private Connection connection;

    public Status find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Status status = null;

        try {
            callableStatement = connection.prepareCall("{call get_status_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                status = new Status();
                status.setId(resultSet.getLong(1));
                status.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<Status> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet;
        List<Status> statuses = new ArrayList<>();
        Status status;

        try {
            callableStatement = connection.prepareCall("{call get_all_statuses()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                status = new Status();
                status.setId(resultSet.getLong(1));
                status.setName(resultSet.getString(2));
                statuses.add(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statuses;
    }

    public Status findByName(String name) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Status status = null;

        try {
            callableStatement = connection.prepareCall("{call get_status_by_name(?)}");
            callableStatement.setString(1, name);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                status = new Status();
                status.setId(resultSet.getLong(1));
                status.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }
}
