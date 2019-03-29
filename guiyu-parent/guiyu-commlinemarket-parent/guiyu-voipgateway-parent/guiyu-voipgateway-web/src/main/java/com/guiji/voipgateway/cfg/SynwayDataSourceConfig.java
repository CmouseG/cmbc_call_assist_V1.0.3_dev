package com.guiji.voipgateway.cfg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.guiji.voipgateway.synway.dao",sqlSessionFactoryRef = "synwaySqlSessionFactory")
public class SynwayDataSourceConfig {

    @Primary
    @Bean(name = "synwayDataSource")
    @ConfigurationProperties("spring.datasource.synway")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "synwaySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("synwayDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mappers/synway/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "synwayTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("synwayDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "synwaySqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("synwaySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
