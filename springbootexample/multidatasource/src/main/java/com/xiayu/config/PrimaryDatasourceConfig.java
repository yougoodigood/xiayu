package com.xiayu.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.xiayu.constants.DataSourceConstants;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = DataSourceConstants.PRIMARY_DATASOURCE_PREFIX)
@MapperScan(basePackages = { DataSourceConstants.MAPPER_BASEPACKAGE }, sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDatasourceConfig {

    private String url;

    private String username;

    private String password;

    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource primaryDataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(url);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(DataSourceConstants.DRIVER_CLASS_NAME);
        return dataSource;
    }

    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(primaryDataSource());
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(primaryDataSource);
        return sessionFactoryBean.getObject();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
