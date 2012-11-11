package ee.ut.physic.aerosol.simulator.util;

import dialect.ImprovedSQLiteDialect;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.context.annotation.*;
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
@ImportResource({"classpath*:/META-INF/persistence.xml"})
@PropertySource({"classpath:config/database.properties", "file:etc/conf/settings.properties"})
@EnableTransactionManagement
@ComponentScan("ee.ut.physic.aerosol.simulator")
public class SpringConfiguration {
          /*
    public @Bean TransferService transferService() {
        return new TransferServiceImpl(accountRepository());
    }

    public @Bean AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }       */

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:etc/database/ags.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }

    /*
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    } */

    @Bean
    public Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("hibernate.dialect", ImprovedSQLiteDialect.class.getName());
        //props.put("javax.persistence.validation.factory", validator());
        props.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
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
        //entityManager.setJpaProperties( additionlProperties() );
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
