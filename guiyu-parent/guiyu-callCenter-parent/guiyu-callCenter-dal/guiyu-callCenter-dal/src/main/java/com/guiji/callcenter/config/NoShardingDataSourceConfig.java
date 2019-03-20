package com.guiji.callcenter.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//扫描mapper接口包
@MapperScan(basePackages = "com.guiji.*.daoNoSharing",sqlSessionFactoryRef = "sqlSessionFactoryNoSharing")
public class NoShardingDataSourceConfig {

	@Value("${jdbc_driver0}")
	private String jdbc_driver0;
	@Value("${jdbc_url0}")
	private String jdbc_url0;
	@Value("${jdbc_username0}")
	private String jdbc_username0;
	@Value("${jdbc_password0}")
	private String jdbc_password0;

	@Bean(name = "dataSourceNoSharding")
	public DataSource getDataSource2() throws SQLException {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUsername(jdbc_username0);
		dataSource.setPassword(jdbc_password0);
		dataSource.setDriverClassName(jdbc_driver0);
		dataSource.setUrl(jdbc_url0);
		return dataSource;
	}

	//定义sqlSessionFactory的bean
	@Bean(name = "sqlSessionFactoryNoSharing")
	//使用@Qualifier注解同样是注入bean，但该注入方式是查找bean的name
	//@Autowired注入是根据bean的类型来查找bean注入
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("dataSourceNoSharding") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//扫描mapper.xml文件
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/noSharding/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

}
