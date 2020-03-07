package com.xiayu.config;

import com.xiayu.constants.DataSourceConstants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Data
@Configuration
@ConfigurationProperties(prefix = DataSourceConstants.MASTER_DATASOURCE_PREFIX)
public class MasterDataSourceConfig {

    private String url = "jdbc:mysql://127.0.0.1:3306/datasource_master?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false";

    private String username = "root";

    private String password = "root";

    public MasterDataSourceConfig() {
    }

//    @Bean("masterDataSource")
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(DataSourceConstants.DRIVER_CLASS_NAME);
        return dataSource;
    }
}
