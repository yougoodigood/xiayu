package com.xiayu.config;


import com.google.common.collect.Lists;
import com.xiayu.constants.DataSourceConstants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Configuration
@ConfigurationProperties(prefix = DataSourceConstants.SHARDING_DATASOURCE_PREFIX)
@EnableConfigurationProperties
//@AutoConfigureAfter(value = {PrimaryDatasourceConfig.class,FirstSlaveDatasourceConfig.class,SecondSlaveDatasourceConfig.class})
@MapperScan(basePackages = { DataSourceConstants.MAPPER_BASEPACKAGE }, sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class ShardingDatasourceConfig {

    private String url;

    private String username;

    private String password;

    public ShardingDatasourceConfig() {
    }

    private DataSource getDataSource(String datasourceName){
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

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getUserTableRuleConfiguration());//表进行分库并进行分表
        shardingRuleConfig.getTableRuleConfigs().add(getUserDetailTableRuleConfiguration()); //表进行分库并进行分表
        shardingRuleConfig.getTableRuleConfigs().add(new TableRuleConfiguration("t_order","ds_1.t_order"));//垂直分库
        shardingRuleConfig.getTableRuleConfigs().add(new TableRuleConfiguration("t_business","ds_2.t_business"));//垂直分库
        shardingRuleConfig.getBindingTableGroups().add("t_user, t_user_detail"); //绑定表
        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations()); //主从设置 一主多从
        shardingRuleConfig.setDefaultDataSourceName("ds_1"); //设置默认的数据源
        shardingRuleConfig.getBroadcastTables().add("t_address"); //设置广播表
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(getDataSourceMap(), shardingRuleConfig,new Properties());
        return dataSource;
    }

    private TableRuleConfiguration getUserTableRuleConfiguration(){
        TableRuleConfiguration userTableRuleConfiguration = new TableRuleConfiguration("t_user","ds_${1..2}.t_user_${1..2}");
        userTableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "id", getProperties()));
        userTableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds_${id % 2 + 1}"));
        userTableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_user_${id % 2 + 1}"));
        return userTableRuleConfiguration;
    }

    private TableRuleConfiguration getUserDetailTableRuleConfiguration(){
        TableRuleConfiguration userDetailTableRuleConfiguration = new TableRuleConfiguration("t_user_detail","ds_${1..2}.t_user_detatil_${1..2}");
        userDetailTableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "id", getProperties()));
        userDetailTableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds_${id % 2 + 1}"));
        userDetailTableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_user_detail_${id % 2 + 1}"));
        return userDetailTableRuleConfiguration;
    }

    private Properties getProperties() {
        Properties result = new Properties();
        result.setProperty("worker.id", "123");
        return result;
    }

    private Map<String,DataSource> getDataSourceMap(){
        Map<String,DataSource> dataSourceMap = new HashMap<>();
        DataSource primaryDatasourceOne = getDataSource("1");
        DataSource firstSlaveDatasourceOne = getDataSource("2");
        DataSource secondSlaveDatasourceOne = getDataSource("3");
        DataSource primaryDatasourceTwo = getDataSource("4");
        DataSource firstSlaveDatasourceTwo = getDataSource("5");
        DataSource secondSlaveDatasourceTwo = getDataSource("6");

        dataSourceMap.put("ds_master_1",primaryDatasourceOne);
        dataSourceMap.put("ds_slave_1_1",firstSlaveDatasourceOne);
        dataSourceMap.put("ds_slave_1_2",secondSlaveDatasourceOne);

        dataSourceMap.put("ds_master_2",primaryDatasourceTwo);
        dataSourceMap.put("ds_slave_2_1",firstSlaveDatasourceTwo);
        dataSourceMap.put("ds_slave_2_2",secondSlaveDatasourceTwo);
        return dataSourceMap;
    }

    private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations(){

        MasterSlaveRuleConfiguration masterSlaveRuleConfigOne = new MasterSlaveRuleConfiguration("ds_1", "ds_master_1", Arrays.asList("ds_slave_1_1", "ds_slave_1_2"));
        MasterSlaveRuleConfiguration masterSlaveRuleConfigTwo = new MasterSlaveRuleConfiguration("ds_2", "ds_master_2", Arrays.asList("ds_slave_2_1", "ds_slave_2_2"));

        return Lists.newArrayList(masterSlaveRuleConfigOne,masterSlaveRuleConfigTwo);
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
