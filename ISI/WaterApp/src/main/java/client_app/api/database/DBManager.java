package client_app.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static final String CONNECTION_STRING = "jdbc:mysql://" +
            DBProperties.IP + ":" + DBProperties.PORT;
    private static Connection connection = null;
    
    private DBManager() throws UnsupportedOperationException {
    }

    public static void registerDriver() {
        try {
            Class.forName(DBProperties.DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(CONNECTION_STRING, DBProperties.USER, DBProperties.PASS);
            } catch (SQLException e) {
            	e.printStackTrace();
                System.exit(1);
            }	
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            	e.printStackTrace();
                System.exit(1);
            }
        }
    }
    
    public static void checkConnection(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME ='" + DBProperties.DATABASE_NAME + "'");
            ResultSet rs = stmt.getResultSet();
            
            while (rs.next()) {
            	System.out.println(rs.getString(1));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
