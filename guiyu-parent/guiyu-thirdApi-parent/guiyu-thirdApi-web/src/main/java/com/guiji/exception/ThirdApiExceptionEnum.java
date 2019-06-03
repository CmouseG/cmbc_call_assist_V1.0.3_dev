package com.guiji.exception;

/**
 * 错误枚举类
 */
public enum ThirdApiExceptionEnum implements ExceptionEnum {

    //处理成功
    SUCCESS(0, "0000", "处理成功"),

    //验签失败
    VERIFY_SIGN_ERROR(1, "1003", "验签失败"),

    //系统性异常
    SYSTEM_ERROR(1, "1004", "系统处理异常"),

    //按条件未查询到相关数据
    QUERY_NO_RESULT(0, "1005", "查询无结果"),

    //查询时，查询量超过5000
    QUEYR_OVER_NUM(1, "1006", "查询数量超限或不合法"),

    //返回结果加签失败
    SIGN_ERROR(1, "1007", "加签失败"),

    //请求时使用了不支持的签名算法，目前仅支持MD5
    SIGN_TYPE_ERROR(1, "1008", "暂不支持的签名算法"),

    //请求时，使用了未知的clientID
    NO_THIS_USER(1, "1009", "无此用户"),

    //请求时，业务参数缺少
    ILLEGAL_ARG(1, "1010", "业务参数不合法"),

    //添加策略时，发生异常
    ADD_STRATEGY_ERROR(1, "101001", "添加策略失败"),

    //查询策略时，发生异常
    QUERY_STRATEGY_ERROR(1, "101002", "查询策略失败"),

    //添加批次时发生异常
    ADD_BATCH_PLAN_ERROR(1, "101003", "添加批次失败"),

    //批量暂停计划时异常
    PAUSE_ERROR(1, "101004", "批量暂停失败"),

    //批量停止时异常
    STOP_ERROR(1, "101005", "批量停止失败"),

    //重启失败
    RESTART_ERROR(1, "101006", "批量重启失败"),

    /**
     * 批次名重复
     */
    DUPLICATE_BATCH_NAME(1, "101007", "批次名已存在"),

    /**
     * 批次名重复
     */
    OVER_LIMIT_PLAN(1, "101008", "任务数量超限");



    private Integer success;
    //返回码
    private String errorCode;
    //返回信息
    private String msg;

    @Override
    public Integer getSuccess() {
        return success;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMsg() {
        return msg;
    }
    @Override
    public String getName() {
        return this.name();
    }

    //根据枚举的code获取msg的方法
    public static ThirdApiExceptionEnum getMsgByErrorCode(String errorCode) {
        for (ThirdApiExceptionEnum exceptionEnum : ThirdApiExceptionEnum.values()) {
            if (exceptionEnum.getErrorCode().equals(errorCode)) {
                return exceptionEnum;
            }
        }
        return null;
    }

    ThirdApiExceptionEnum(Integer success, String errorCode, String msg) {
        this.success = success;
        this.errorCode = errorCode;
        this.msg = msg;
    }

}