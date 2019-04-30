package com.guiji.callcenter.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/** PreciseShardingAlgorithm是必选的，用于处理=和IN的分片。 */
public class OrgIdPreciseSharding implements PreciseShardingAlgorithm<Long> {
  private String tablePre;

  public OrgIdPreciseSharding(String tablePre) {
    this.tablePre = tablePre;
  }

  @Override
  public String doSharding(
      Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
    return tablePre + preciseShardingValue.getValue();
  }
}
