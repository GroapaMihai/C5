package ro.transport.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.LinkedCaseInsensitiveMap;
import ro.transport.demo.domain.Employee;
import ro.transport.demo.domain.RevenueIncrease;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeDao {
    @Autowired
    private Connection connection;

    @Autowired
    private AccountTypeDao accountTypeDao;

    @Autowired
    private StatusDao statusDao;

    public Employee add(Employee employee) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call add_employee(?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, employee.getFirstName());
            callableStatement.setString(2, employee.getLastName());
            callableStatement.setDate(3, employee.getHireDate());
            callableStatement.setString(4, employee.getEmail());
            callableStatement.setString(5, employee.getPhone());
            callableStatement.setLong(6, employee.getAccountType().getId());
            callableStatement.setLong(7, employee.getStatus().getId());
            callableStatement.setString(8, employee.getPassword());
            callableStatement.setInt(9, employee.getSalary());
            callableStatement.setBigDecimal(10, employee.getComm());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public Employee find(Long id) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Employee employee = null;

        try {
            callableStatement = connection.prepareCall("{call get_employee_by_id(?)}");
            callableStatement.setLong(1, id);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getLong(1));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setHireDate(resultSet.getDate(4));
                employee.setEmail(resultSet.getString(5));
                employee.setPhone(resultSet.getString(6));
                employee.setAccountType(accountTypeDao.find(resultSet.getLong(7)));
                employee.setStatus(statusDao.find(resultSet.getLong(8)));
                employee.setPassword(resultSet.getString(9));
                employee.setSalary(resultSet.getInt(10));
                employee.setComm(resultSet.getBigDecimal(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public Employee update(Employee employee) {
        CallableStatement callableStatement;

        try {
            callableStatement = connection.prepareCall("{call update_employee(?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setLong(1, employee.getId());
            callableStatement.setString(2, employee.getFirstName());
            callableStatement.setString(3, employee.getLastName());
            callableStatement.setDate(4, employee.getHireDate());
            callableStatement.setString(5, employee.getEmail());
            callableStatement.setString(6, employee.getPhone());
            callableStatement.setLong(7, employee.getAccountType().getId());
            callableStatement.setLong(8, employee.getStatus().getId());
            callableStatement.setString(9, employee.getPassword());
            callableStatement.setInt(10, employee.getSalary());
            callableStatement.setBigDecimal(11, employee.getComm());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    private List<Employee> extractEmployeesFromResultSet(ResultSet resultSet) {
        List<Employee> employees = new ArrayList<>();
        Employee employee;

        if (resultSet == null) {
            return employees;
        }

        try {
            while (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getLong(1));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setHireDate(resultSet.getDate(4));
                employee.setEmail(resultSet.getString(5));
                employee.setPhone(resultSet.getString(6));
                employee.setAccountType(accountTypeDao.find(resultSet.getLong(7)));
                employee.setStatus(statusDao.find(resultSet.getLong(8)));
                employee.setPassword(resultSet.getString(9));
                employee.setSalary(resultSet.getInt(10));
                employee.setComm(resultSet.getBigDecimal(11));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Employee> findAll() {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_all_employees()}");
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractEmployeesFromResultSet(resultSet);
    }

    public List<Employee> findByStatusName(String statusName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_employees_by_status_name(?)}");
            callableStatement.setString(1, statusName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractEmployeesFromResultSet(resultSet);
    }

    public List<Employee> findByAccountTypeName(String accountTypeName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_employees_by_account_type_name(?)}");
            callableStatement.setString(1, accountTypeName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractEmployeesFromResultSet(resultSet);
    }

    public List<Employee> findByAccountTypeNameAndStatusName(String accountTypeName, String statusName) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_employees_by_account_type_name_and_status_name(?,?)}");
            callableStatement.setString(1, accountTypeName);
            callableStatement.setString(2, statusName);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractEmployeesFromResultSet(resultSet);
    }

    public Employee findByEmailAndPassword(String email, String password) {
        CallableStatement callableStatement;
        ResultSet resultSet;
        Employee employee = null;

        try {
            callableStatement = connection.prepareCall("{call get_employee_by_email_and_password(?,?)}");
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();

            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getLong(1));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setHireDate(resultSet.getDate(4));
                employee.setEmail(resultSet.getString(5));
                employee.setPhone(resultSet.getString(6));
                employee.setAccountType(accountTypeDao.find(resultSet.getLong(7)));
                employee.setStatus(statusDao.find(resultSet.getLong(8)));
                employee.setPassword(resultSet.getString(9));
                employee.setSalary(resultSet.getInt(10));
                employee.setComm(resultSet.getBigDecimal(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public void delete(Long id) {

    }

    public List<Employee> getEligibleDriversForRevenueIncrease(RevenueIncrease revenueIncrease) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call get_employees_eligible_for_revenue_inc(?,?,?,?,?)}");
            callableStatement.setInt(1, revenueIncrease.getMinDeliveries());
            callableStatement.setString(2, revenueIncrease.getOperator());
            callableStatement.setInt(3, revenueIncrease.getMinCompanyRevenueFromDriver());
            callableStatement.setDate(4, revenueIncrease.getStartDate());
            callableStatement.setDate(5, revenueIncrease.getEndDate());
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractEmployeesFromResultSet(resultSet);
    }

    public List<Employee> increaseRevenues(RevenueIncrease revenueIncrease) {
        CallableStatement callableStatement;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall("{call apply_revenue_inc(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setBigDecimal(1, revenueIncrease.getSalaryIncrease());
            callableStatement.setString(2, revenueIncrease.getSalaryIncreaseType());
            callableStatement.setBigDecimal(3, revenueIncrease.getCommIncrease());
            callableStatement.setString(4, revenueIncrease.getCommIncreaseType());
            callableStatement.setInt(5, revenueIncrease.getMinDeliveries());
            callableStatement.setString(6, revenueIncrease.getOperator());
            callableStatement.setInt(7, revenueIncrease.getMinCompanyRevenueFromDriver());
            callableStatement.setDate(8, revenueIncrease.getStartDate());
            callableStatement.setDate(9, revenueIncrease.getEndDate());
            callableStatement.executeUpdate();
            resultSet = callableStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return extractEmployeesFromResultSet(resultSet);
    }
}
