
package com.guiji.callcenter.sharding;


import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;


/**
 * PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。
 */

public class PreciseShardingString implements PreciseShardingAlgorithm<String> {

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

