package ro.transport.demo.domain;

import java.math.BigDecimal;
import java.sql.Date;

public class RevenueIncrease {
    private BigDecimal salaryIncrease;
    private String salaryIncreaseType;
    private BigDecimal commIncrease;
    private String commIncreaseType;
    private Integer minDeliveries;
    private String operator;
    private Integer minCompanyRevenueFromDriver;
    private Date startDate;
    private Date endDate;

    public BigDecimal getSalaryIncrease() {
        return salaryIncrease;
    }

    public void setSalaryIncrease(BigDecimal salaryIncrease) {
        this.salaryIncrease = salaryIncrease;
    }

    public String getSalaryIncreaseType() {
        return salaryIncreaseType;
    }

    public void setSalaryIncreaseType(String salaryIncreaseType) {
        this.salaryIncreaseType = salaryIncreaseType;
    }

    public BigDecimal getCommIncrease() {
        return commIncrease;
    }

    public void setCommIncrease(BigDecimal commIncrease) {
        this.commIncrease = commIncrease;
    }

    public String getCommIncreaseType() {
        return commIncreaseType;
    }

    public void setCommIncreaseType(String commIncreaseType) {
        this.commIncreaseType = commIncreaseType;
    }

    public Integer getMinDeliveries() {
        return minDeliveries;
    }

    public void setMinDeliveries(Integer minDeliveries) {
        this.minDeliveries = minDeliveries;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getMinCompanyRevenueFromDriver() {
        return minCompanyRevenueFromDriver;
    }

    public void setMinCompanyRevenueFromDriver(Integer minCompanyRevenueFromDriver) {
        this.minCompanyRevenueFromDriver = minCompanyRevenueFromDriver;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RevenueIncrease that = (RevenueIncrease) o;

        if (!salaryIncrease.equals(that.salaryIncrease)) return false;
        if (!salaryIncreaseType.equals(that.salaryIncreaseType)) return false;
        if (!commIncrease.equals(that.commIncrease)) return false;
        if (!commIncreaseType.equals(that.commIncreaseType)) return false;
        if (!minDeliveries.equals(that.minDeliveries)) return false;
        if (!operator.equals(that.operator)) return false;
        if (!minCompanyRevenueFromDriver.equals(that.minCompanyRevenueFromDriver)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = salaryIncrease.hashCode();
        result = 31 * result + salaryIncreaseType.hashCode();
        result = 31 * result + commIncrease.hashCode();
        result = 31 * result + commIncreaseType.hashCode();
        result = 31 * result + minDeliveries.hashCode();
        result = 31 * result + operator.hashCode();
        result = 31 * result + minCompanyRevenueFromDriver.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RevenueIncrease{" +
                "salaryIncrease=" + salaryIncrease +
                ", salaryIncreaseType='" + salaryIncreaseType + '\'' +
                ", commIncrease=" + commIncrease +
                ", commIncreaseType='" + commIncreaseType + '\'' +
                ", minDeliveries=" + minDeliveries +
                ", operator='" + operator + '\'' +
                ", minCompanyRevenueFromDriver=" + minCompanyRevenueFromDriver +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
