package ro.transport.demo.services;

import ro.transport.demo.domain.AccountType;
import ro.transport.demo.domain.Employee;
import ro.transport.demo.domain.RevenueIncrease;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface EmployeeService {
    Employee add(Employee employee);

    Employee find(Long id);

    List<Employee> findAll();

    List<Employee> findByAccountTypeName(String accountTypeName);

    List<Employee> findByStatusName(String statusName);

    List<Employee> findByAccountTypeNameAndStatusName(String accountTypeName, String statusName);

    Employee findByEmailAndPassword(String email, String password);

    Employee update(Employee employee);

    void delete(Long id);

    List<Employee> getEligibleDriversForRevenueIncrease(RevenueIncrease revenueIncrease);

    List<Employee> increaseRevenues(RevenueIncrease revenueIncrease);
}
