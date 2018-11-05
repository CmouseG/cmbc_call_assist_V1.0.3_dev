package com.guiji.callcenter.sharding;

import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;


@Configuration
public class  ShardingDataSourceConfig {

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
        DataSource dataSource0 = getDataSource(0);
        DataSource dataSource1 = getDataSource(1);
        Map<String, DataSource> map = new HashMap<>();
        map.put("guiyu_callcenter",dataSource0);
        map.put("guiyu_callcenter1",dataSource1);
        return map;
    }

    public DataSource getDataSource(int i){
        org.apache.tomcat.jdbc.pool.DataSource  dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl(LoadProperties.getProperty("jdbc_url"+i));
        dataSource.setUsername(LoadProperties.getProperty("jdbc_username"+i));
        dataSource.setPassword(LoadProperties.getProperty("jdbc_password"+i));
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
    
    /**
     * 配置表规则
     * @return
     */
    private List<TableRuleConfiguration> tableRuleConfigurations(){
    	TableRuleConfiguration result = new TableRuleConfiguration();
    	result.setLogicTable("call_out_plan");
    	result.setActualDataNodes("guiyu_callcenter.call_out_plan,guiyu_callcenter1.call_out_plan");
    	result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id",new PreciseSharding(),new RangeSharding()));
    	return Arrays.asList(result);
    }

}
