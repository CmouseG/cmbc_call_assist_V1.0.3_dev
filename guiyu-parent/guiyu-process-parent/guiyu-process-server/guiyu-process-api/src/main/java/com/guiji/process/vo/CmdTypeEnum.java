package com.guiji.process.vo;

public enum CmdTypeEnum {

    START(0), STOP(1), HEALTH(1), RESTART(2), REGISTER(3), RESTORE_MODEL(4), UNKNOWN(-1);

    /** 标识DeviceTypeEnmu的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private CmdTypeEnum(int value)
    {
        this.iValue = value;
    }


    /**
     * 返回DeviceTypeEnmu的整型值
     * @return DeviceTypeEnmu的整型值
     */
    public int getValue()
    {
        return iValue;
    }


    /**
     * 根据整型值构建DeviceTypeEnmu实例
     * @param value 整型值
     * @return ErrorCodeEnum实例
     */
    public static CmdTypeEnum valueOf(int value)
    {
        for (CmdTypeEnum type : CmdTypeEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}
