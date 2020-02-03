package com.xiayu.config;

import com.xiayu.constants.DataSourceConstants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Data
@Configuration
@ConfigurationProperties(prefix = DataSourceConstants.MASTER_DATASOURCE_PREFIX)
public class MasterDataSourceConfig {

    private String url;

    private String username;

    private String password;

    public MasterDataSourceConfig() {
    }

    @Bean("masterDataSource")
    @Primary
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(DataSourceConstants.DRIVER_CLASS_NAME);
        return dataSource;
    }
}
