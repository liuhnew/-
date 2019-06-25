package com.jykj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ActivitiDataSourceConfig extends AbstractProcessEngineAutoConfiguration {

    @Value("${first.datasource.url}")
    String firstDataSourceUrl;
    @Value("${first.datasource.username}")
    String firstDataSourceUsername;
    @Value("${first.datasource.password}")
    String firstDataSourcePassword;
    @Value("${first.datasource.driverClassName}")
    String firstDataSourceDriverClassName;

    @Bean("activitiDataSource")
    public DataSource activitiDataSource() {
        DruidDataSource DruiddataSource = new DruidDataSource();
        DruiddataSource.setUrl(firstDataSourceUrl);
        DruiddataSource.setDriverClassName(firstDataSourceDriverClassName);
        DruiddataSource.setPassword(firstDataSourcePassword);
        DruiddataSource.setUsername(firstDataSourceUsername);
        return DruiddataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(activitiDataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(activitiDataSource());
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setAnnotationFontName("宋体");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager());
        return configuration;
    }

}
