package com.guiji.dispatch.algorithm;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.ShardingStrategyConfiguration;
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
        dataSource.setUrl("jdbc:mysql://47.97.179.12:3306/guiyu_dispatch?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
        dataSource.setUsername("dispatch");
        dataSource.setPassword("dispatch@1234");
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
//    	result.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("user_id",new ShardingStrategy()));
    	result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id",new PreciseSharding(),new RangeSharding()));
    	return Arrays.asList(result);
    }

}