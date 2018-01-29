package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ro.transport.demo.domain.Priority;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriorityDao {
    @Autowired
    private Connection connection;

    public Priority find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Priority priority = null;

        try {
            callableStatement = connection.prepareCall("{call get_priority_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                priority = new Priority();
                priority.setId(resultSet.getLong(1));
                priority.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return priority;
    }

    public List<Priority> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet;
        List<Priority> priorities = new ArrayList<>();
        Priority priority;

        try {
            callableStatement = connection.prepareCall("{call get_all_priorities()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                priority = new Priority();
                priority.setId(resultSet.getLong(1));
                priority.setName(resultSet.getString(2));
                priorities.add(priority);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return priorities;
    }

    public Priority findByName(String name) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Priority priority = null;

        try {
            callableStatement = connection.prepareCall("{call get_priority_by_name(?)}");
            callableStatement.setString(1, name);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                priority = new Priority();
                priority.setId(resultSet.getLong(1));
                priority.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return priority;
    }
}
