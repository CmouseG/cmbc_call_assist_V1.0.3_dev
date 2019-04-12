package com.guiji.ai.common;

public class Result
{
	/**
	 * 返回成功
	 * @param object 返回数据对象
	 * @return
	 */
	public static <T> ReturnData<T> success(T object)
	{
		ReturnData<T> result = new ReturnData<T>();
		result.setCode("0");
		result.setMsg("success");
		result.setData(object);
		return result;
	}

	/**
	 * 返回成功-无返回值
	 * @return
	 */
	public static <T> ReturnData<T> success()
	{
		return success(null);
	}

	/**
	 * 返回失败-返回已定义异常信息
	 * @param exception 自定义异常信息
	 * @return
	 */
	public static <T> ReturnData<T> error(AiException exception)
	{
		ReturnData<T> result = new ReturnData<T>();
		result.setCode(exception.getCode());
		result.setMsg(exception.getMessage());
		result.setData(null);
		return result;
	}

	/**
	 * 返回失败-自定义错误信息
	 * @param code
	 * @param msg
	 * @return
	 */
	public static <T> ReturnData<T> error(String code, String msg)
	{
		ReturnData<T> result = new ReturnData<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(null);
		return result;
	}

}
