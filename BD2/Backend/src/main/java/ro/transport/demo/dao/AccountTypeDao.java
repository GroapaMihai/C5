package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ro.transport.demo.domain.AccountType;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountTypeDao {
    @Autowired
    private Connection connection;

    public AccountType find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        AccountType accountType = null;

        try {
            callableStatement = connection.prepareCall("{call get_account_type_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                accountType = new AccountType();
                accountType.setId(resultSet.getLong(1));
                accountType.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountType;
    }

    public List<AccountType> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet;
        List<AccountType> accountTypes = new ArrayList<>();
        AccountType accountType;

        try {
            callableStatement = connection.prepareCall("{call get_all_account_types()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            while (resultSet.next()) {
                accountType = new AccountType();
                accountType.setId(resultSet.getLong(1));
                accountType.setName(resultSet.getString(2));
                accountTypes.add(accountType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountTypes;
    }

    public AccountType findByName(String name) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        AccountType accountType = null;

        try {
            callableStatement = connection.prepareCall("{call get_account_type_by_name(?)}");
            callableStatement.setString(1, name);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                accountType = new AccountType();
                accountType.setId(resultSet.getLong(1));
                accountType.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountType;
    }
}
