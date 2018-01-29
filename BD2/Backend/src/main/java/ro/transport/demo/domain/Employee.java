package ro.transport.demo.domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Date hireDate;
    private String email;
    private String phone;
    private AccountType accountType;
    private Status status;
    private String password;
    private Integer salary;
    private BigDecimal comm;

    public Employee() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public BigDecimal getComm() {
        return comm;
    }

    public void setComm(BigDecimal comm) {
        this.comm = comm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!id.equals(employee.id)) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (!lastName.equals(employee.lastName)) return false;
        if (!hireDate.equals(employee.hireDate)) return false;
        if (!email.equals(employee.email)) return false;
        if (phone != null ? !phone.equals(employee.phone) : employee.phone != null) return false;
        if (!accountType.equals(employee.accountType)) return false;
        if (!password.equals(employee.password)) return false;
        if (salary != null ? !salary.equals(employee.salary) : employee.salary != null) return false;
        if (comm != null ? !comm.equals(employee.comm) : employee.comm != null) return false;
        return status.equals(employee.status);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + lastName.hashCode();
        result = 31 * result + hireDate.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + accountType.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (comm != null ? comm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate=" + hireDate +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", accountType=" + accountType +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", salary=" + salary +
                ", comm=" + comm +
                '}';
    }
}
