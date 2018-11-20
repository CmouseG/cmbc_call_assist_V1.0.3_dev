
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

public class RangeShardingString implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        Collection<String> collect = new ArrayList<>();
        Range<String> valueRange = rangeShardingValue.getValueRange();
        for (long i = Long.valueOf(valueRange.lowerEndpoint()); i <= Long.valueOf(valueRange.upperEndpoint()); i++) {
            for (String each : collection) {
                if (each.endsWith(i % collection.size() + "")) {
                    collect.add(each);
                }
            }
        }
        return collect;
    }


}

