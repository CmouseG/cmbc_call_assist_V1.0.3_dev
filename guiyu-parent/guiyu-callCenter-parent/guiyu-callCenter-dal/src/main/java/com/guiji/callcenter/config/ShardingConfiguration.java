package com.guiji.callcenter.config;

import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ShardingConfiguration {

	private Boolean sqlShow;
	private List<TableRuleConfiguration> tableRuleConfigurations;
	private List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations;
	private Map<String, DataSource> dataSourceMap;

	public DataSource createShardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.setTableRuleConfigs(tableRuleConfigurations);
		shardingRuleConfig.setMasterSlaveRuleConfigs(masterSlaveRuleConfigurations);
		Properties properties = new Properties();
		properties.setProperty("sql.show", (sqlShow == null ? false : sqlShow) + "");
//		properties.setProperty("sql.show",  true+"" );
		DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
				new HashMap<>(), properties);
		return dataSource;
	}

	public void setSqlShow(Boolean sqlShow) {
		this.sqlShow = sqlShow;
	}

	public void setTableRuleConfigurations(List<TableRuleConfiguration> tableRuleConfigurations) {
		this.tableRuleConfigurations = tableRuleConfigurations;
	}

	public void setMasterSlaveRuleConfigurations(List<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations) {
		this.masterSlaveRuleConfigurations = masterSlaveRuleConfigurations;
	}

	public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

	public Boolean getSqlShow() {
		return sqlShow;
	}

	/**
	 * 分表规则
	 * 
	 * @return
	 */
	public List<TableRuleConfiguration> getTableRuleConfigurations() {
		return tableRuleConfigurations;
	}

	/**
	 * 配置主从
	 * 
	 * @return
	 */
	public List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
		return masterSlaveRuleConfigurations;
	}

	public Map<String, DataSource> getDataSourceMap() {
		return dataSourceMap;
	}
}
