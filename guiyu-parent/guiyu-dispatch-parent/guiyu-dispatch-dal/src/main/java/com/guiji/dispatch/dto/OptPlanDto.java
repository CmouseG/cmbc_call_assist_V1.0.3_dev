package com.guiji.dispatch.dto;


import java.util.List;

public class OptPlanDto extends QueryPlanListDto {

    /**
     * 类型   1：全选    2:只勾选   3：全选去勾
     */
    private Integer type;

    /**
     * 只勾选（在不全选的情况下勾选）
     */
    private List<Long> checkPlanUuid;

    /**
     * 全选再去勾情况
     */
    private List<Long> nocheckPlanUuid;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Long> getCheckPlanUuid() {
        return checkPlanUuid;
    }

    public void setCheckPlanUuid(List<Long> checkPlanUuid) {
        this.checkPlanUuid = checkPlanUuid;
    }

    public List<Long> getNocheckPlanUuid() {
        return nocheckPlanUuid;
    }

    public void setNocheckPlanUuid(List<Long> nocheckPlanUuid) {
        this.nocheckPlanUuid = nocheckPlanUuid;
    }
}
