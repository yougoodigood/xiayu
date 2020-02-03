package com.xiayu.config;

import com.xiayu.constants.DataSourceConstants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Data
@Configuration
@ConfigurationProperties(prefix = DataSourceConstants.SLAVE_ONE_DATASOURCE_PREFIX)
public class SlaveOneDataSourceConfig {

    private String jdbcurl;

    private String username;

    private String password;

    public SlaveOneDataSourceConfig() {
    }

    @Bean("slaveOneDataSource")
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcurl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(DataSourceConstants.DRIVER_CLASS_NAME);
        return dataSource;
    }
}
