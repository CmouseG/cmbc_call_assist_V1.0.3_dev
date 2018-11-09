package com.guiji.dispatch.algorithm;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;

import com.alibaba.druid.pool.DruidDataSource;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.util.LoadProperties;

import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;


@Configuration
public class ShardingDataSourceConfig {

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
        map.put("guiyu_dispatch",dataSource);
        return map;
    }

    public DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(LoadProperties.getProperty("jdbc_url0"));
        dataSource.setUsername(LoadProperties.getProperty("jdbc_username0"));
        dataSource.setPassword(LoadProperties.getProperty("jdbc_password0"));
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
    
    /**
     * 配置表规则
     * @return
     */
    private List<TableRuleConfiguration> tableRuleConfigurations(){
    	TableRuleConfiguration result = new TableRuleConfiguration();
    	result.setLogicTable("dispatch_plan");
    	result.setActualDataNodes("guiyu_dispatch.dispatch_plan_0,guiyu_dispatch.dispatch_plan_1,guiyu_dispatch.dispatch_plan_2");
//    	result.setActualDataNodes("guiyu_dispatch.dispatch_plan_0,guiyu_dispatch.dispatch_plan_1");
    	result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("phone",new PreciseSharding(),new RangeSharding()));
    	return Arrays.asList(result);
    }

}
