package com.guiji.process.vo;

public enum DeviceTypeEnum {

    TTS(0), SELLBOT(1), AGENT(99),UNKNOWN(-1);

    /** 标识DeviceTypeEnmu的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private DeviceTypeEnum(int value)
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
    public static DeviceTypeEnum valueOf(int value)
    {
        for (DeviceTypeEnum type : DeviceTypeEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}
