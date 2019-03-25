package com.guiji.utils.IdGengerator;

public class IdVO {
  private long id;

  private long workerId;

  private long orgId;

  private long sequence;

  private long createTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getWorkerId() {
    return workerId;
  }

  public void setWorkerId(long workerId) {
    this.workerId = workerId;
  }

  public Integer getOrgId() {
    return Integer.valueOf(""+orgId);
  }

  public void setOrgId(long orgId) {
    this.orgId = orgId;
  }

  public long getSequence() {
    return sequence;
  }

  public void setSequence(long sequence) {
    this.sequence = sequence;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "IdVO{"
        + "id="
        + id
        + ", workerId="
        + workerId
        + ", orgId="
        + orgId
        + ", sequence="
        + sequence
        + ", createTime="
        + createTime
        + '}';
  }
}
