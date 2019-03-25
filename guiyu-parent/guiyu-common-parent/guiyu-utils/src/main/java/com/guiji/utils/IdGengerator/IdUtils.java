package com.guiji.utils.IdGengerator;

public class IdUtils {

  public static IdVO doParse(Long id) {

    IdVO vo = new IdVO();

    vo.setSequence(id & getBitMask(SnowflakeIdWorker.sequenceBits));

    long workIdShift = id >>> SnowflakeIdWorker.workerIdShift;
    vo.setWorkerId(workIdShift & getBitMask(SnowflakeIdWorker.workerIdBits));

    long datacenterShift = id >>> SnowflakeIdWorker.dataCenterIdShift;
    vo.setOrgId(datacenterShift & getBitMask(SnowflakeIdWorker.orgCodeIdBits));

    long recoverTime = id >>> SnowflakeIdWorker.timestampShift;
    vo.setCreateTime(SnowflakeIdWorker.twepoch + recoverTime);

    vo.setId(id);
    return vo;
  }

  private static long getBitMask(Long bit) {
    return -1L ^ -1L << bit;
  }
}
