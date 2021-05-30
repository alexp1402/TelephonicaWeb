package org.callservice.configuration;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "org.callservice.repositories")
@EnableTransactionManagement
@EnableSpringDataWebSupport
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

    @Autowired
    private Environment env;

//    public PersistenceConfig() {
//
//        super();
//    }

    /**
     * Creates the bean DataSource
     *
     * @param env The runtime environment of  our application.
     * @return
     */

    @Bean
    DataSource dataSource(Environment env) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }


    /**
     * Creates the bean that creates the JPA entity manager factory.
     *
     * @param dataSource The datasource that provides the database connections.
     * @param env        The runtime environment of  our application.
     * @return
     */
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        //package with Entity
        entityManagerFactoryBean.setPackagesToScan(new String[]{"org.callservice.models"});
        //hibernate implementation of JpaVendorAdapter
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        //add Hibernate Properties
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        return entityManagerFactoryBean;
    }

    /**
     * Creates the transaction manager bean that integrates the used JPA provider with the
     * Spring transaction mechanism.
     *
     * @param emf The used JPA entity manager factory.
     * @return
     */
    @Bean
    JpaTransactionManager transactionManager(final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * Creates the properties for Hibernate from application.properties file in resources folder
     */

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        //Specifies the action that is invoked to the database when the Hibernate SessionFactory is created or closed.
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        //Configures the used database dialect. This allows Hibernate to create SQL
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        //Configures the naming strategy that is used when Hibernate creates new database objects and schema elements
        hibernateProperties.setProperty("hibernate.ejb.naming_strategy", env.getProperty("hibernate.ejb.naming_strategy"));
        //Write sql in console
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        //Format sql for console (pretty)
        hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

        hibernateProperties.setProperty("hibernate.useUnicode", env.getProperty("hibernate.useUnicode"));
        hibernateProperties.setProperty("hibernate.characterEncoding", env.getProperty("hibernate.characterEncoding"));
        hibernateProperties.setProperty("hibernate.CharSet", env.getProperty("hibernate.CharSet"));


        // hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        return hibernateProperties;
    }
}
