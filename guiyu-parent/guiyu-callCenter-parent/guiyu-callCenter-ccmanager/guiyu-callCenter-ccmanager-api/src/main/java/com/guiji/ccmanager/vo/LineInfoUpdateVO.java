package com.guiji.ccmanager.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(description= "线路信息")
public class LineInfoUpdateVO extends LineInfoAddVO implements Serializable {

    @ApiModelProperty(value = "线路id")
    @NotNull(message = "线路id不能为空")
    private int lineId;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    @Override
    public String toString() {
        return "LineInfoUpdateVO{" +
                "lineId=" + lineId +
                "} " + super.toString();
    }
}