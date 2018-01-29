package ro.transport.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ro.transport.demo.dao.*;
import ro.transport.demo.services.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = {"ro.transport.demo.controllers"})
@EntityScan(value = "ro.transport.demo.domain")
public class ProjectConfiguration {

    private static final String PACKAGES_TO_SCAN = "ro.transport.demo.domain";
    private static final String USERNAME_DATABASE = "postgres";
    private static final String PASSWORD_DATABASE = "postgres";
    private static final String URL_DATABASE = "jdbc:postgresql://localhost:5432/Transport_Logistics_DB";
    private static final String ENTITY_MANAGER_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername(USERNAME_DATABASE);
        dataSource.setPassword(PASSWORD_DATABASE);
        dataSource.setUrl(URL_DATABASE);

        return dataSource;
    }

    @Bean
    public Connection getConnectionBean() {
        try {
            return dataSource().getConnection();
        } catch (SQLException e) {
            System.out.println("Could not retrieve connection bean!");
        }

        return null;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(ENTITY_MANAGER_DIALECT);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setJpaVendorAdapter(jpaVendorAdapter());
        emfb.setPackagesToScan(PACKAGES_TO_SCAN);

        return emfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    public AccountTypeServiceImpl getAccountTypeServiceImplBean() {
        return new AccountTypeServiceImpl();
    }

    @Bean
    public AddressServiceImpl getAddressServiceImplBean() {
        return new AddressServiceImpl();
    }

    @Bean
    public ColorServiceImpl getColorServiceImplBean() {
        return new ColorServiceImpl();
    }

    @Bean
    public CountryServiceImpl getCountryServiceImplBean() {
        return new CountryServiceImpl();
    }

    @Bean
    public DeliveryServiceImpl getDeliveryServiceImplBean() {
        return new DeliveryServiceImpl();
    }

    @Bean
    public EmployeeServiceImpl getEmployeeServiceImplBean() {
        return new EmployeeServiceImpl();
    }

    @Bean
    public PlanificationServiceImpl getPlanificationServiceImplBean() {
        return new PlanificationServiceImpl();
    }

    @Bean
    public PriorityServiceImpl getPriorityServiceImplBean() {
        return new PriorityServiceImpl();
    }

    @Bean
    public StatusServiceImpl getStatusServiceImplBean() {
        return new StatusServiceImpl();
    }

    @Bean
    public VehicleServiceImpl getVehicleServiceImplBean() {
        return new VehicleServiceImpl();
    }

    @Bean
    public AccountTypeDao getAccountTypeDaoBean() {
        return new AccountTypeDao();
    }

    @Bean
    public AddressDao getAddressDaoBean() {
        return new AddressDao();
    }

    @Bean
    public ColorDao getColorDaoBean() {
        return new ColorDao();
    }

    @Bean
    public CountryDao getCountryDaoBean() {
        return new CountryDao();
    }

    @Bean
    public DeliveryDao getDeliveryDaoBean() {
        return new DeliveryDao();
    }

    @Bean
    public EmployeeDao getEmployeeDaoBean() {
        return new EmployeeDao();
    }

    @Bean
    public PlanificationDao getPlanificationDaoBean() {
        return new PlanificationDao();
    }

    @Bean
    public PriorityDao getPriorityDaoBean() {
        return new PriorityDao();
    }

    @Bean
    public StatusDao getStatusDaoBean() {
        return new StatusDao();
    }

    @Bean
    public VehicleDao getVehicleDaoBean() {
        return new VehicleDao();
    }
}
