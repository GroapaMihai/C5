package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;
import ro.transport.demo.domain.Employee;
import ro.transport.demo.domain.Planification;
import ro.transport.demo.domain.RevenueIncrease;
import ro.transport.demo.services.EmployeeService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/employee/add")
    @ResponseBody
    public Employee add(@RequestBody Employee jsonEmployee) {
        return employeeService.add(jsonEmployee);
    }

    @GetMapping("/employee/id/{id}")
    @ResponseBody
    public Employee find(@PathVariable("id") Long id) {
        return employeeService.find(id);
    }

    @GetMapping("/employee/all")
    @ResponseBody
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employee/accountType/{accountTypeName}")
    @ResponseBody
    public List<Employee> findByAccountTypeName(@PathVariable("accountTypeName") String accountTypeName) {
        return employeeService.findByAccountTypeName(accountTypeName);
    }

    @GetMapping("/employee/status/{statusName}")
    @ResponseBody
    public List<Employee> findByStatusName(@PathVariable("statusName") String statusName) {
        return employeeService.findByStatusName(statusName);
    }

    @GetMapping("/employee/accountType/status/{accountTypeName}/{statusName}")
    @ResponseBody
    public List<Employee> findByAccountTypeNameAndStatusName(@PathVariable("accountTypeName") String accountTypeName, @PathVariable("statusName") String statusName) {
        return employeeService.findByAccountTypeNameAndStatusName(accountTypeName, statusName);
    }

    @GetMapping("/employee/email/password/{email}/{password}")
    @ResponseBody
    public Employee findByEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password) {
        return employeeService.findByEmailAndPassword(email, password);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PutMapping("/employee/update/id/{id}")
    @ResponseBody
    public Employee update(@RequestBody Employee jsonEmployee) {
        return employeeService.update(jsonEmployee);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @DeleteMapping("/employee/delete/id/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/employee/eligibleDrivers")
    @ResponseBody
    public List<Employee> eligibleDrivers(@RequestBody RevenueIncrease jsonRevenueIncrease) {
        return employeeService.getEligibleDriversForRevenueIncrease(jsonRevenueIncrease);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/employee/driversRevenueIncrease")
    @ResponseBody
    public List<Employee> increaseRevenues(@RequestBody RevenueIncrease jsonRevenueIncrease) {
        return employeeService.increaseRevenues(jsonRevenueIncrease);
    }
}
