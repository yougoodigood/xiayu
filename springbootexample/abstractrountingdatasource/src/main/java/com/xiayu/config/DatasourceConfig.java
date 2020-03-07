package com.xiayu.config;


import com.xiayu.constants.DataSourceConstants;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.greenrobot.eventbus.EventBus;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@MapperScan(basePackages = { DataSourceConstants.MAPPER_BASEPACKAGE }, sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {

//    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

//    @Qualifier("slaveOneDataSource")
    private DataSource slaveOneDataSource;

//    @Qualifier("slaveTwoDataSource")
    private DataSource slaveTwoDataSource;

    public DatasourceConfig() {

    }

    @Bean(name = "xiayuDataSource")
    @Primary
    public DataSource xiayuDataSource(){
        MasterDataSourceConfig masterDataSourceConfig = new MasterDataSourceConfig();
        this.masterDataSource = masterDataSourceConfig.dataSource();

        SlaveOneDataSourceConfig slaveOneDataSourceConfig = new SlaveOneDataSourceConfig();
        this.slaveOneDataSource = slaveOneDataSourceConfig.dataSource();

        SlaveTwoDataSourceConfig slaveTwoDataSourceConfig = new SlaveTwoDataSourceConfig();
        this.slaveTwoDataSource = slaveTwoDataSourceConfig.dataSource();

        XiayuDataSource xiayuDataSource = new XiayuDataSource();
        Map<Object,Object> dataSourceMap = new HashMap<>(); //这儿的key是object,如果determineCurrentLookupKey()方法返回的值与此key相等则会路由到对应的数据源上
        dataSourceMap.put("masterDataSource",masterDataSource); //如果lookupkey返回值和masterDataSource相同，就会返回masterDataSource
        dataSourceMap.put("slaveOneDataSource",slaveOneDataSource);
        dataSourceMap.put("slaveTwoDataSource",slaveTwoDataSource);
        xiayuDataSource.setTargetDataSources(dataSourceMap);
        xiayuDataSource.setDefaultTargetDataSource(masterDataSource); //默认的数据源
        return xiayuDataSource;
    }

//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager transactionManager(@Qualifier("xiayuDataSource")DataSource xiayuDataSource) throws SQLException{
//        return new DataSourceTransactionManager(xiayuDataSource);
//    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("xiayuDataSource")DataSource xiayuDataSource) throws SQLException{
        return new DataSourceTransactionManager(xiayuDataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("xiayuDataSource") DataSource xiayuDataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(xiayuDataSource);
        return sessionFactoryBean.getObject();
    }



}
