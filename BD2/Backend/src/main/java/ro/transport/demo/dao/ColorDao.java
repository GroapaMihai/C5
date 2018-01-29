package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ro.transport.demo.domain.Color;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorDao {
    @Autowired
    private Connection connection;

    public Color find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Color color = null;

        try {
            callableStatement = connection.prepareCall("{call get_color_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                color = new Color();
                color.setId(resultSet.getLong(1));
                color.setName(resultSet.getString(2));
                color.setHex(resultSet.getString(3));
                color.setRgb(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return color;
    }

    public List<Color> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet;
        List<Color> colors = new ArrayList<>();
        Color color;

        try {
            callableStatement = connection.prepareCall("{call get_all_colors()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                color = new Color();
                color.setId(resultSet.getLong(1));
                color.setName(resultSet.getString(2));
                color.setHex(resultSet.getString(3));
                color.setRgb(resultSet.getString(4));
                colors.add(color);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colors;
    }
}
