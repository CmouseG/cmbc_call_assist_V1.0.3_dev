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
@MapperScan(basePackages = "com.guiji.voipgateway.dingxin.dao",sqlSessionFactoryRef = "dingxinSqlSessionFactory")
public class DingxinDataSourceConfig {

    @Primary
    @Bean(name = "dingxinDataSource")
    @ConfigurationProperties("spring.datasource.dingxin")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dingxinSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dingxinDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mappers/dingxin/*.xml"));
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "dingxinTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("dingxinDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dingxinSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("dingxinSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
