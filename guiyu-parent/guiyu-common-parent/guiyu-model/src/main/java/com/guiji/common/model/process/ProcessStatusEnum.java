package com.guiji.common.model.process;

public enum ProcessStatusEnum {

    UP(0), DOWN(1), BUSYING(2), MISSING(3), UNKNOWN(-1),REGISTER(4),UNREGISTER(5);

    /** 标识DeviceTypeEnmu的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private ProcessStatusEnum(int value)
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
    public static ProcessStatusEnum valueOf(int value)
    {
        for (ProcessStatusEnum type : ProcessStatusEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}
