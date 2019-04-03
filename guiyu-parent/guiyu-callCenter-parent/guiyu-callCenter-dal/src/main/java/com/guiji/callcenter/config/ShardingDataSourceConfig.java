package com.guiji.callcenter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.guiji.callcenter.sharding.PreciseShardingInt;
import com.guiji.callcenter.sharding.PreciseShardingString;
import com.guiji.callcenter.sharding.RangeShardingInt;
import com.guiji.callcenter.sharding.RangeShardingString;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@MapperScan(basePackages = "com.guiji.*.dao",sqlSessionFactoryRef = "sqlSessionFactoryPrimary")
public class ShardingDataSourceConfig {

	@Value("${jdbc_driver0}")
	private String jdbc_driver0;
	@Value("${jdbc_url0}")
	private String jdbc_url0;
	@Value("${jdbc_username0}")
	private String jdbc_username0;
	@Value("${jdbc_password0}")
	private String jdbc_password0;
	@Value("${server.id}")
	private String workId;

	@Bean(name = "dataSourcePrimary")
	@Primary
	public DataSource getShardingDataSource() throws SQLException {
		ShardingConfiguration shardingConfiguration = new ShardingConfiguration();
		shardingConfiguration.setDataSourceMap(createDataSourceMap());
		shardingConfiguration.setMasterSlaveRuleConfigurations(new ArrayList<>());
		shardingConfiguration.setTableRuleConfigurations(tableRuleConfigurations());
		DataSource shardingDataSource = shardingConfiguration.createShardingDataSource();
		return shardingDataSource;
	}


	public Map<String, DataSource> createDataSourceMap() {
		DataSource dataSource = getDataSource();
		Map<String, DataSource> map = new HashMap<>();
		map.put("guiyu_callcenter", dataSource);
		return map;
	}

	public DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();

		dataSource.setUrl(jdbc_url0);
		dataSource.setUsername(jdbc_username0);
		dataSource.setPassword(jdbc_password0);
		dataSource.setDriverClassName(jdbc_driver0);
		
		//修改配置
		dataSource.setMinIdle(5);
		dataSource.setInitialSize(5);
		dataSource.setMaxActive(200);
		return dataSource;
	}

	/**
	 * 配置表规则
	 * 
	 * @return
	 */
	private List<TableRuleConfiguration> tableRuleConfigurations() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("call_out_plan");
		result.setActualDataNodes("guiyu_callcenter.call_out_plan_0,guiyu_callcenter.call_out_plan_1");
		result.setTableShardingStrategyConfig( new StandardShardingStrategyConfiguration(
				"phone_num", new PreciseShardingString(), new RangeShardingString()));
		DefaultKeyGenerator.setWorkerId(Long.valueOf(workId));
		result.setKeyGeneratorColumnName("call_id");

		TableRuleConfiguration result2 = new TableRuleConfiguration();
		result2.setLogicTable("call_out_detail");
		result2.setActualDataNodes("guiyu_callcenter.call_out_detail_0,guiyu_callcenter.call_out_detail_1");
		result2.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(
				"sharding_value", new PreciseShardingInt(), new RangeShardingInt()));
		result2.setKeyGeneratorColumnName("call_detail_id");

		List<TableRuleConfiguration> reslutList = new ArrayList<>();
		reslutList.add(result);
		reslutList.add(result2);

		return reslutList;
	}
	//定义sqlSessionFactory的bean
	@Bean(name = "sqlSessionFactoryPrimary")
	@Primary
	//使用@Qualifier注解同样是注入bean，但该注入方式是查找bean的name
	//@Autowired注入是根据bean的类型来查找bean注入
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("dataSourcePrimary") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//扫描mapper.xml文件
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
}
