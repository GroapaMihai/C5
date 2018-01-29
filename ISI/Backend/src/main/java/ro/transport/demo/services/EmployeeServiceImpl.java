package ro.transport.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.transport.demo.dao.EmployeeDao;
import ro.transport.demo.dao.StatusDao;
import ro.transport.demo.domain.Employee;
import ro.transport.demo.domain.RevenueIncrease;
import ro.transport.demo.domain.Status;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private StatusDao statusDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee add(Employee employee) {
        Status status = statusDao.findByName("Available");
        employee.setStatus(status);

        return employeeDao.add(employee);
    }

    @Override
    public Employee find(Long id) {
        return employeeDao.find(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public List<Employee> findByAccountTypeName(String accountTypeName) {
        return employeeDao.findByAccountTypeName(accountTypeName);
    }

    @Override
    public List<Employee> findByStatusName(String statusName) {
        if (statusName.equals("All")) {
            return findAll();
        }

        return employeeDao.findByStatusName(statusName);
    }

    @Override
    public List<Employee> findByAccountTypeNameAndStatusName(String accountTypeName, String statusName) {
        return employeeDao.findByAccountTypeNameAndStatusName(accountTypeName, statusName);
    }

    @Override
    public Employee findByEmailAndPassword(String email, String password) {
        return employeeDao.findByEmailAndPassword(email, password);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeDao.update(employee);
    }

    @Override
    public void delete(Long id) {
        employeeDao.delete(id);
    }

    @Override
    public List<Employee> getEligibleDriversForRevenueIncrease(RevenueIncrease revenueIncrease) {
        return employeeDao.getEligibleDriversForRevenueIncrease(revenueIncrease);
    }

    @Override
    public List<Employee> increaseRevenues(RevenueIncrease revenueIncrease) {
        return employeeDao.increaseRevenues(revenueIncrease);
    }
}
