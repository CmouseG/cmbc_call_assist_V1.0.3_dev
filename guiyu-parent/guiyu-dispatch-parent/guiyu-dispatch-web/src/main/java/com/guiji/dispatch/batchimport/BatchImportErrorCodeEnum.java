package com.guiji.dispatch.batchimport;

public enum BatchImportErrorCodeEnum {

    SELLBOT_CHECK_ERROR(1), SELLBOT_CHECK_PARAM(2),DUPLICATE(3),UNKNOWN(-1);

    /** 标识BatchImportErrorCodeEnum的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private BatchImportErrorCodeEnum(int value)
    {
        this.iValue = value;
    }


    /**
     * 返回BatchImportErrorCodeEnum的整型值
     * @return BatchImportErrorCodeEnum的整型值
     */
    public int getValue()
    {
        return iValue;
    }


    /**
     * 根据整型值构建BatchImportErrorCodeEnum实例
     * @param value 整型值
     * @return BatchImportErrorCodeEnum实例
     */
    public static BatchImportErrorCodeEnum valueOf(int value)
    {
        for (BatchImportErrorCodeEnum type : BatchImportErrorCodeEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return UNKNOWN;
    }
}
