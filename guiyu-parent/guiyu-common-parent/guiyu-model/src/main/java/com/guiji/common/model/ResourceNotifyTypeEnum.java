package com.guiji.common.model;

/**
 *
 */
public enum  ResourceNotifyTypeEnum {

    BOTSTENCE_UPDATE(1), UNKNOWN(-1);

    /** 标识ResourceNotifyTypeEnum的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private ResourceNotifyTypeEnum(int value)
    {
        this.iValue = value;
    }


    /**
     * ResourceNotifyTypeEnum
     * @return ResourceNotifyTypeEnum的整型值
     */
    public int getValue()
    {
        return iValue;
    }


    /**
     * 根据整型值构建ResourceNotifyTypeEnum实例
     * @param value 整型值
     * @return ErrorCodeEnum实例
     */
    public static ResourceNotifyTypeEnum valueOf(int value)
    {
        for (ResourceNotifyTypeEnum type : ResourceNotifyTypeEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}
