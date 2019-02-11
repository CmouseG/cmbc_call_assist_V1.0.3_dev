package com.guiji.dispatch.algorithm;

import java.util.Collection;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

/**
 * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 */
public class PreciseShardingLines implements PreciseShardingAlgorithm<Integer> {

	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
		  for (String name : collection) {
	            if (name.endsWith(Long.valueOf(preciseShardingValue.getValue()) % collection.size() + "")) {
	                return name;
	            }
	        }
	        return null;
	}


}
