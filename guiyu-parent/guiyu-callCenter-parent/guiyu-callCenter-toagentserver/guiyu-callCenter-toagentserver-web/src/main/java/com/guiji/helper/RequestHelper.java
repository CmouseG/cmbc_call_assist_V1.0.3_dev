package com.guiji.helper;

import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: 魏驰
 * @Date: 2018-10-31 17:28:00
 * @Description:  用于循环的请求接口数据，直至请求到数据为止
 */
@Slf4j
public class RequestHelper {
    public static boolean isSuccess(Result.ReturnData result){
        if(result == null){
            return false;
        }

        String code = result.getCode();
        if("0".equals(code)){
            return true;
        }else{
            String resultCode = code.substring(code.length()-3);
            return "000".equals(resultCode);
        }
    }

    /**
     * 循环请求接口数据，出现异常会停止请求
     * @param requestApi
     * @param retryTimes    重试次数，如果为-1.则不断重试
     * @param retryInterval 重试间隔，单位为秒
     * @param delaySeconds  每次失败后，在原有的重试间隔上面增加的递增秒数，为0，则不需要递增秒数
     *
     */
    public static  Result.ReturnData loopRequest(RequestApi requestApi, int retryTimes, int retryInterval, int delaySeconds, int maxRetryInterval) throws Exception {
        return  loopRequest( requestApi,  retryTimes,  retryInterval,  delaySeconds,  maxRetryInterval, false);
    }

    /**
     * 循环请求接口数据
     * @param requestApi
     * @param retryTimes    重试次数，如果为-1.则不断重试
     * @param retryInterval 重试间隔，单位为秒
     * @param delaySeconds  每次失败后，在原有的重试间隔上面增加的递增秒数，为0，则不需要递增秒数
     * @param isContinueOnException 出现异常是否继续请求，true继续，false不继续
     */
    public static  Result.ReturnData loopRequest(RequestApi requestApi, int retryTimes, int retryInterval, int delaySeconds, int maxRetryInterval, boolean isContinueOnException) throws Exception {
        if(retryInterval<=0){
            throw new Exception("重试间隔需要 大于0");
        }

        if(delaySeconds<0){
            throw new Exception("重试延迟秒数需要大于等于0");
        }

        boolean unStopFlag = (retryTimes<=-1);
        boolean isContinue = true;
        int totalDelaySeconds = 0;

        do{
            try{
                Result.ReturnData result = requestApi.execute();
                if(result!=null){
                    //请求成功立刻返回
                    if(result.success){
                        isContinue = false;
                        return result;
                    }else{
                        requestApi.onErrorResult(result);
                        if(requestApi.trueBreakOnCode(result.getCode())){
                            isContinue = false;
                            return null;
                        }
                    }
                }
            }catch (Exception e){// 出现异常 ，还会继续循环调用
                log.error("requestApi.execute  error:",e);
                if(!isContinueOnException){//出现异常，无需继续，直接跳出
                    break;
                }
            }finally {
                if(!unStopFlag){
                    retryTimes--;
                }
                if(isContinue && (unStopFlag || retryTimes>0)) {
                    totalDelaySeconds += delaySeconds;
                    int totalSleepSeconds = (retryInterval + totalDelaySeconds);
                    if (totalSleepSeconds >= maxRetryInterval) {
                        totalSleepSeconds = maxRetryInterval;
                    }
                    Thread.sleep(totalSleepSeconds * 1000L);
                }else{
                    isContinue = false;
                }
            }

        }while (isContinue);

        return null;
    }

    public interface RequestApi{
        Result.ReturnData execute();

        void onErrorResult(Result.ReturnData result);

        boolean trueBreakOnCode(String code);
    }
}
