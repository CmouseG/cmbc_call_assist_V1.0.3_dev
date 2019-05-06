package com.guiji.callcenter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.guiji.callcenter.sharding.PreciseShardingInt;
import com.guiji.callcenter.sharding.PreciseShardingString;
import com.guiji.callcenter.sharding.RangeShardingInt;
import com.guiji.callcenter.sharding.RangeShardingString;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		DefaultKeyGenerator.setWorkerId(Long.valueOf(workId));

		List<TableRuleConfiguration> list = new ArrayList<>();

		String shardingColumn = "org_id";
		list.add(createTblConfig("call_out_plan",shardingColumn,
						new OrgIdPreciseSharding("call_out_plan_"),
						new OrgIdRangeSharding("call_out_plan_"),"call_id"));
		list.add(createTblConfig("call_out_detail",shardingColumn,
						new OrgIdPreciseSharding("call_out_detail_"),
						new OrgIdRangeSharding("call_out_detail_"),"call_detail_id"));
		return list;
	}

	private TableRuleConfiguration createTblConfig(String logicTable,String shardingColumn,
												   PreciseShardingAlgorithm preciseSharding,
												   RangeShardingAlgorithm rangeSharding,String keyColumn) {

		TableRuleConfiguration tblConfig = new TableRuleConfiguration();
		tblConfig.setLogicTable(logicTable);

		tblConfig.setTableShardingStrategyConfig(
				new StandardShardingStrategyConfiguration(shardingColumn, preciseSharding, rangeSharding));
		tblConfig.setKeyGeneratorColumnName(keyColumn);
		return tblConfig;
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
