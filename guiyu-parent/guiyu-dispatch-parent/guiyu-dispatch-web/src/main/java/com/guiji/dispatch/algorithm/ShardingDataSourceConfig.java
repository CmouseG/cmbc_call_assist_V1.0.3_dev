package com.guiji.dispatch.algorithm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import io.shardingsphere.core.routing.strategy.ShardingAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.guiji.dispatch.util.LoadProperties;

import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;

@Configuration
public class ShardingDataSourceConfig {

  @Value("${jdbc_driver0}")
  private String jdbc_driver0;

  @Value("${jdbc_url0}")
  private String jdbc_url0;

  @Value("${jdbc_username0}")
  private String jdbc_username0;

  @Value("${jdbc_password0}")
  private String jdbc_password0;

  @Bean
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
    map.put("guiyu_dispatch", dataSource);
    return map;
  }

  public DataSource getDataSource() {
    DruidDataSource dataSource = new DruidDataSource();

    dataSource.setUrl(jdbc_url0);
    dataSource.setUsername(jdbc_username0);
    dataSource.setPassword(jdbc_password0);
    dataSource.setDriverClassName(jdbc_driver0);

    // 修改配置
    dataSource.setMinIdle(5);
    dataSource.setInitialSize(5);
    dataSource.setMaxActive(20);
    return dataSource;
  }

  /**
   * 配置表规则
   *
   * @return
   */
  private List<TableRuleConfiguration> tableRuleConfigurations() {
    List<TableRuleConfiguration> list = new ArrayList<>();

    String logicTable = "dispatch_plan";
    String shardingColumn = "org_id";
    list.add(
        createTblConfig(
            logicTable,
            shardingColumn,
            new DispatchPlanPreciseSharding(logicTable + "_"),
            new DispatchPlanRangeSharding(logicTable + "_")));
    return list;
  }

  private TableRuleConfiguration createTblConfig(
      String logicTable,
      String shardingColumn,
      PreciseShardingAlgorithm preciseSharding,
      RangeShardingAlgorithm rangeSharding) {

    TableRuleConfiguration tblConfig = new TableRuleConfiguration();
    tblConfig.setLogicTable(logicTable);
    //		tblConfig.setActualDataNodes(
    //
    //	"guiyu_dispatch.dispatch_plan_0,guiyu_dispatch.dispatch_plan_1,guiyu_dispatch.dispatch_plan_2");
    tblConfig.setTableShardingStrategyConfig(
        new StandardShardingStrategyConfiguration(shardingColumn, preciseSharding, rangeSharding));

    return tblConfig;
  }
}
