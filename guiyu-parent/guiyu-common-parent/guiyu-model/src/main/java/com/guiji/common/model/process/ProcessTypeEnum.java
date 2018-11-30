package com.guiji.common.model.process;

public enum ProcessTypeEnum {

    TTS(0), SELLBOT(1), FREESWITCH(2),ROBOT(3),AGENT(99),UNKNOWN(-1);

    /** 标识DeviceTypeEnmu的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private ProcessTypeEnum(int value)
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
    public static ProcessTypeEnum valueOf(int value)
    {
        for (ProcessTypeEnum type : ProcessTypeEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}
