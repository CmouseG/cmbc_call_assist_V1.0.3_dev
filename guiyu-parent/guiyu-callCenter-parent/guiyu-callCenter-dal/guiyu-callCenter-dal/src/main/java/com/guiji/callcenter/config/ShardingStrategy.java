package com.guiji.callcenter.config;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分表算法：
 */
public class ShardingStrategy implements ComplexKeysShardingAlgorithm{

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			Collection<ShardingValue> shardingValues) {
        List<String> result = new ArrayList<>();
        String value = ((ListShardingValue) shardingValues.toArray()[0]).getValues().toArray()[0].toString();

        int i = value.hashCode() % availableTargetNames.size();
        Object o = availableTargetNames.toArray()[i];
        result.add((String) o);
        return result;
	}
}
