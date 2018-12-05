package com.guiji.process.core.vo;

public enum CmdTypeEnum {

    UNKNOWN(-1),  STOP(1), HEALTH(2), RESTART(3), REGISTER(4), RESTORE_MODEL(5), UNREGISTER(6),AGENTREGISTER(7),PULBLISH_SELLBOT_BOTSTENCE(8),PULBLISH_FREESWITCH_BOTSTENCE(9),START(10),PUBLISH_ROBOT_BOTSTENCE(11),AFTER_RESTART(12),AFTER_RESTORE_MODEL(13),DO_NOTHING(14);

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
