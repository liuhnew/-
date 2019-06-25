package com.jykj.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
/**
**  @Author: gaozhixing
**  @Date: 2018/3/18 21:28
**  @Description:
**/
@Configuration
/**
**  @Author: gaozhixing
**  @Company: exsun
**  @Date: 2018/3/18 21:36
**  @Description:
**/
public class DataSourceConfig {
	@Autowired
    private Environment env;
    @Value("${first.datasource.url}")
    String firstDataSourceUrl;
    @Value("${first.datasource.username}")
    String firstDataSourceUsername;
    @Value("${first.datasource.password}")
    String firstDataSourcePassword;
    @Value("${first.datasource.driverClassName}")
    String firstDataSourceDriverClassName;

    @Primary
	@Bean(name = "firstDataSource")
    public DataSource firstDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        System.out.println(firstDataSourceUrl);
        dataSource.setUrl(firstDataSourceUrl);
        dataSource.setUsername(firstDataSourceUsername);//用户名
        dataSource.setPassword(firstDataSourcePassword);//密码
        dataSource.setDriverClassName(firstDataSourceDriverClassName);
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(0);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(false);
        dataSource.setDefaultAutoCommit(false);//设定不自动提交
        return dataSource;
    }

}
