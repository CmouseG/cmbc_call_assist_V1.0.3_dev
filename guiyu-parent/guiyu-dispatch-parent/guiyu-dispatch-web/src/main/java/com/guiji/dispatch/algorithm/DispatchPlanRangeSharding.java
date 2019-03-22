package com.guiji.dispatch.algorithm;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片， 如果不配置RangeShardingAlgorithm，SQL中的BETWEEN
 * AND将按照全库路由处理
 */
public class DispatchPlanRangeSharding implements RangeShardingAlgorithm<Long> {

  private String tablePre;

  public DispatchPlanRangeSharding(String tablePre) {
    this.tablePre = tablePre;
  }

  @Override
  public Collection<String> doSharding(
      Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {

    Collection<String> collect = new ArrayList<>();

    Range<Long> valueRange = rangeShardingValue.getValueRange();

    for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
      collect.add(this.tablePre + i);
    }
    return collect;
  }
}
