package config;

import dialect.ImprovedSQLiteDialect;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:config/database.properties"})
@EnableTransactionManagement
@ComponentScan("ee.ut.physic.aerosol.simulator")
public class SpringConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String driverClassName = environment.getProperty("database.driverClassName");
        String protocol = environment.getProperty("database.protocol");
        dataSource.setDriverClassName(driverClassName);
        ee.ut.physic.aerosol.simulator.Configuration simulatorConfiguration = ee.ut.physic.aerosol.simulator.Configuration.getInstance();
        String databasePath;
        if (simulatorConfiguration.getUserSettings().containsKey("database.path")) {
            databasePath = simulatorConfiguration.getUserSettings().getProperty("database.path");
        } else {
            databasePath = environment.getProperty("database.defaultPath");
        }
        // If relative path, then change it to absolute path
        if (!databasePath.startsWith("/")) {
            databasePath = simulatorConfiguration.getRootPath() + databasePath;
        }
        String databaseUrl = protocol + databasePath;
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("hibernate.dialect", ImprovedSQLiteDialect.class.getName());
        props.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        props.put("hibernate.connection.charSet", "UTF-8");
        //props.put("hibernate.hbm2ddl.auto", "create");
        return props;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("ee.ut.physic.aerosol.simulator");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {{
        }};
        vendorAdapter.setShowSql(true);
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaPropertyMap(jpaProperties());
        //entityManager.setJpaProperties(additionalProperties());
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
