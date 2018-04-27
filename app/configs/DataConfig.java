package configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import play.db.DB;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataConfig {
    //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html
    /**
     * This function creates the desired EntityManagerFactory, which is part of the Spring framework used for injection.
     * This also sets up Hibernate with the desired settings.
     * @return an EntityManagerFactory with the proper properties
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabase(Database.MYSQL);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("jpa");
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    /**
     * @return PlatformTransactionManager. Currently this method is unused in this project, so unsure of its use.
     * Likely internal to Spring Framework.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    /**
     * @return the datasource from the play framework.
     */
    @Bean
    public DataSource dataSource() {
        // Return the datasource from the play framework.
        return DB.getDataSource();
    }
}