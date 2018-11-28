package com.guiji.process.core.message;

public enum CmdMsgTypeEnum {

     REQ(1), REQ_ACK(2);

    /** 标识CmdMsgTypeEnum的整型值 */
    private int iValue;


    /**
     * 构造方法
     * @param value 整型值
     */
    private CmdMsgTypeEnum(int value)
    {
        this.iValue = value;
    }


    /**
     * 返回CmdMsgTypeEnum的整型值
     * @return CmdMsgTypeEnum的整型值
     */
    public int getValue()
    {
        return iValue;
    }


    /**
     * 根据整型值构建CmdMsgTypeEnum实例
     * @param value 整型值
     * @return CmdMsgTypeEnum实例
     */
    public static CmdMsgTypeEnum valueOf(int value)
    {
        for (CmdMsgTypeEnum type : CmdMsgTypeEnum.values())
        {
            if (value == type.getValue())
            {
                return type;
            }
        }
        return null;
    }
}
