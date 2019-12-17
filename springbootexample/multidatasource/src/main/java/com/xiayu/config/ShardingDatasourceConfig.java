package com.xiayu.config;

import com.sun.tools.javac.util.Assert;
import com.xiayu.constants.DataSourceConstants;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter
@ConfigurationProperties(DataSourceConstants.SHARDING_DATASOURCE_PREFIX)
@MapperScan(basePackages = { DataSourceConstants.MAPPER_BASEPACKAGE }, sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class ShardingDatasourceConfig {

    private String url = "jdbc:mysql://127.0.0.1:3306/multidatasource%s?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false";

    private String username = "root";

    private String password = "root";

    public DataSource getDataSource(String datasourceName){
        HikariDataSource dataSource = new HikariDataSource();
        String jdbcUrl = String.format(url,datasourceName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(DataSourceConstants.DRIVER_CLASS_NAME);
        return dataSource;
    }

    @Bean("shardingDataSource")
    @Primary
    public DataSource shardingDataSource() throws SQLException {
        Map<String,DataSource> dataSourceMap = new HashMap<>();

        DataSource primaryDatasource = getDataSource("1");
        DataSource firstSlaveDatasource = getDataSource("2");
        DataSource secondSlaveDatasource = getDataSource("3");

        Assert.checkNonNull(primaryDatasource,"primaryDatasource is null");

        dataSourceMap.put("ds_master",primaryDatasource);
        dataSourceMap.put("ds_slave1",firstSlaveDatasource);
        dataSourceMap.put("ds_slave2",secondSlaveDatasource);

        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("ds_1");
        masterSlaveRuleConfig.setMasterDataSourceName("ds_master");
        masterSlaveRuleConfig.getSlaveDataSourceNames().add("ds_slave1");
        masterSlaveRuleConfig.getSlaveDataSourceNames().add("ds_slave2");

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getMasterSlaveRuleConfigs().add(masterSlaveRuleConfig);

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig);
        return dataSource;
    }

    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("shardingDataSource")DataSource shardingDataSource) throws SQLException{
        return new DataSourceTransactionManager(shardingDataSource);
    }

    @Bean(name = "shardingSqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("shardingDataSource") DataSource primaryDataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(primaryDataSource);
        return sessionFactoryBean.getObject();
    }
}
