package com.guiji.dispatch.algorithm;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.Range;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

/**
 * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 */
public class PreciseSharding implements PreciseShardingAlgorithm<String> {

	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
		  for (String name : collection) {
	            if (name.endsWith(Long.valueOf(preciseShardingValue.getValue()) % collection.size() + "")) {
	                return name;
	            }
	        }
	        return null;
	}


}
