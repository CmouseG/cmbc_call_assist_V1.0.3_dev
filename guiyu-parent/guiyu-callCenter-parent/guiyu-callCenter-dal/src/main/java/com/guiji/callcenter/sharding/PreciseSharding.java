package com.guiji.callcenter.sharding;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 */
public class PreciseSharding implements PreciseShardingAlgorithm<Integer> {

	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
		  for (String name : collection) {
			  System.out.println("====================>>>>>>>>>>>>>>>>>>>>>>"+name);
	            if (name.endsWith(preciseShardingValue.getValue() % collection.size() + "")) {
	                return name;
	            }
	        }
	        return null;
	}


}
