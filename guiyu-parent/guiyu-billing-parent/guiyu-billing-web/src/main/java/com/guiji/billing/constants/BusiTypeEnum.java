package com.guiji.billing.constants;

/**
 * 业务类型枚举
 */
public enum BusiTypeEnum {

    //账户
    BILLING_ACCT("BILLING_ACCT","10","账户", "计费中心"),

    ;

    /**
     * 业务类型
     */
    private String type;
    /**
     *
     * 业务编码
     */
    private String code;
    /**
     * 业务描述
     */
    private String desc;
    /**
     * 业务模块
     */
    private String moduleName;

    BusiTypeEnum(String type, String code, String desc, String moduleName) {
        this.type = type;
        this.code = code;
        this.desc = desc;
        this.moduleName = moduleName;
    }

    public static BusiTypeEnum getEnum(String type) {
        for(BusiTypeEnum busiTypeEnum:BusiTypeEnum.values()){
            if (busiTypeEnum.getType().equals(type)) {
                return busiTypeEnum;
            }
        }
        return null;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
