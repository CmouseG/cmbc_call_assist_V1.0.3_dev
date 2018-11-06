package com.guiji.fsline.util;


import com.guiji.component.result.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: 魏驰
 * @Date: 2018-10-31 17:28:00
 * @Description:  用于循环的请求接口数据，直至请求到数据为止
 */
public class RequestUtil {
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
     * 循环请求接口数据
     * @param requestApi
     * @param retryTimes    重试次数，如果为-1.则不断重试
     * @param retryInterval 重试间隔，单位为秒
     * @param delaySeconds  每次失败后，在原有的重试间隔上面增加的递增秒数，为0，则不需要递增秒数
     */
    public static  Result.ReturnData loopRequest(RequestApi requestApi, int retryTimes, int retryInterval, int delaySeconds, int maxRetryInterval) throws Exception {
        if(retryInterval<=0){
            throw new Exception("重试间隔需要大于0");
        }

        if(delaySeconds<0){
            throw new Exception("重试延迟秒数需要大于等于0");
        }

        boolean unStopFlag = (retryTimes<=-1);
        boolean isContinue = true;
        int totalDelaySeconds = 0;

        do{
            Result.ReturnData result = requestApi.execute();
            if(result!=null){
                //请求成功立刻返回
                if(isSuccess(result)){
                    return result;
                }else{
                    requestApi.onErrorResult(result);
                }
            }

            if(!unStopFlag){
                retryTimes--;
                //请求失败，则不断重试
                if(retryTimes<=0){
                    //超过指定的请求次数，则退出
                    return result;
                }
            }

            totalDelaySeconds+=delaySeconds;
            int totalSleepSeconds = (retryInterval + totalDelaySeconds);
            if(totalSleepSeconds >= maxRetryInterval){
                totalSleepSeconds = maxRetryInterval;
            }
            Thread.sleep(totalSleepSeconds * 1000);

            if(unStopFlag){
                isContinue = true;
            }

            System.out.println(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + ",  retryTimes:"+retryTimes+",totalDelaySeconds = [" + totalDelaySeconds + "], totalSleepSeconds = [" + totalSleepSeconds + "], isContinue = [" + isContinue + "]");
        }while (isContinue);

        return null;
    }

    public interface RequestApi{
        Result.ReturnData execute();

        void onErrorResult(Result.ReturnData result);
    }

    public static void main(String[] args) {
        try {
            loopRequest(new RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    Result.ReturnData result = new Result.ReturnData();
                    result.setCode("101010001");
                    result.setMsg("error from test");
                    return result;
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    System.out.println("返回结果为空, code:"+ result.getCode() + ",msg:" + result.getMsg());

                }
            }, -1, 2, 1,10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
