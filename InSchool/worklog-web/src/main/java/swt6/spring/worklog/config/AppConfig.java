package swt6.spring.worklog.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.logic.WorkLogFacade;
import swt6.spring.worklog.web.EmployeeController;

//@Configuration
//@ImportResource({ "/applicationContext-jpa.xml" })
//public class AppConfig {
//}

// Version 2
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { EmployeeRepository.class })
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = { WorkLogFacade.class, EmployeeRepository.class, EmployeeController.class })
public class AppConfig {
 @Value("${spring.datasource.driver-class-name}")
 private String driverClassName;

 @Value("${spring.datasource.url}")
 private String dbUrl;

 @Value("${spring.datasource.username}")
 private String dbUsername;

 @Value("${spring.datasource.password}")
 private String dbPassword;

 @Value("${spring.jpa.dialect}")
 private String dialect;

 @Value("${spring.jpa.show-sql}")
 private String showSql;

 @Value("${spring.jpa.ddl-auto}")
 private String ddlGeneration;

 private Properties jpaProperties() {
  Properties properties = new Properties();
  properties.setProperty("hibernate.dialect", dialect);
  properties.setProperty("hibernate.show_sql", String.valueOf(showSql));
  properties.setProperty("hibernate.hbm2ddl.auto", ddlGeneration);
  return properties;
 }

 @Bean
 public DataSource dataSource() {
  BasicDataSource dataSource = new BasicDataSource();
  dataSource.setDriverClassName(driverClassName);
  dataSource.setUrl(dbUrl);
  dataSource.setUsername(dbUsername);
  dataSource.setPassword(dbPassword);
  return dataSource;
 }

 @Bean
 public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
  LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
  emFactory.setDataSource(dataSource());
  emFactory.setPackagesToScan("swt6.spring.worklog.domain");
  emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
  emFactory.setJpaProperties(jpaProperties());
  return emFactory;
 }
}