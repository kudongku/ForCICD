package com.six.cicd_setting.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    private static final String MASTER_DATASOURCE = "masterDataSource";
    private static final String SLAVE_DATASOURCE = "slaveDataSource";

    // mater database DataSource
    @Bean(MASTER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    // slave database DataSource
    @Bean(SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    // routing dataSource Bean
    @Bean
    @DependsOn({MASTER_DATASOURCE, SLAVE_DATASOURCE})
    public DataSource routingDataSource (
        @Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
        @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource) {

        RoutingDatasource routingDatasource = new RoutingDatasource();

        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>() {
            {
                put("master", masterDataSource);
                put("slave", slaveDataSource);
            }
        };

        // dataSource Map 설정
        routingDatasource.setTargetDataSources(dataSourceMap);
        // default DataSource는 master로 설정
        routingDatasource.setDefaultTargetDataSource(masterDataSource);

        return routingDatasource;
    }

    // setting lazy connection
    @Bean
    @Primary
    @DependsOn("routingDataSource")
    public LazyConnectionDataSourceProxy dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    // SqlSessionTemplate 에서 사용할 SqlSession 을 생성하는 Factory
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        /*
         * MyBatis 는 JdbcTemplate 대신 Connection 객체를 통한 질의를 위해서 SqlSession 을 사용한다.
         * 내부적으로 SqlSessionTemplate 가 SqlSession 을 구현한다.
         * Thread-Safe 하고 여러 개의 Mapper 에서 공유할 수 있다.
         */
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // MyBatis Mapper Source
        // MyBatis 의 SqlSession 에서 불러올 쿼리 정보
        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
        bean.setMapperLocations(res);

        // MyBatis Config Setting
        // MyBatis 설정 파일
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
        bean.setConfigLocation(myBatisConfig);

        return bean.getObject();
    }

    // DataSource 에서 Transaction 관리를 위한 Manager 클래스 등록
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
