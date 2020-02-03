package com.xiayu.config;


import com.xiayu.constants.DataSourceConstants;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@Setter
@Configuration
@MapperScan(basePackages = { DataSourceConstants.MAPPER_BASEPACKAGE }, sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveOneDataSource")
    private DataSource slaveOneDataSource;

    @Resource(name = "slaveTwoDataSource")
    private DataSource slaveTwoDataSource;

    public DatasourceConfig() {
    }

    @Bean(name = "xiayuDataSource")
    public DataSource xiayuDataSource(){
        DataSource xiayuDataSource = masterDataSource;
//        XiayuDataSource xiayuDataSource = new XiayuDataSource();
//        Map<String,DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("masterDataSource",masterDataSource);
//        dataSourceMap.put("slaveOneDataSource",slaveOneDataSource);
//        dataSourceMap.put("slaveTwoDataSource",slaveTwoDataSource);
//
////        xiayuDataSource.setTargetDataSources(dataSourceMap);
//        xiayuDataSource.setDefaultTargetDataSource(masterDataSource);
        return xiayuDataSource;
    }

//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager transactionManager(@Qualifier("xiayuDataSource")DataSource xiayuDataSource) throws SQLException{
//        return new DataSourceTransactionManager(xiayuDataSource);
//    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("masterDataSource")DataSource masterDataSource) throws SQLException{
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("xiayuDataSource") DataSource xiayuDataSource) throws Exception{
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(xiayuDataSource);
        return sessionFactoryBean.getObject();
    }
}
