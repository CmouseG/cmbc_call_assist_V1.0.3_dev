
package com.guiji.callcenter.sharding;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;


/**
 * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片，
 * 如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理
 *
 */

public class RangeShardingInt implements RangeShardingAlgorithm<Long> {

   @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Collection<String> collect = new ArrayList<>();
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        for (long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            for (String each : collection) {
                if (each.endsWith(i % collection.size() + "")) {
                    collect.add(each);
                }
            }
        }
        return collect;
    }


}

