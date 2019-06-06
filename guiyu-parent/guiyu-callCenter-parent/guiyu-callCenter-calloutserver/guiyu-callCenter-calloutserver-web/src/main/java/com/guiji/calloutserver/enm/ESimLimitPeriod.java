package com.guiji.calloutserver.enm;

/**
 * author:liyang
 * Date:2019/5/28 11:27
 * Description:
 */
public enum ESimLimitPeriod {

    //0-10分钟
    minutes10(10,0),
    //1-20分钟
    minutes20(20,1),
    //2-30分钟
    minutes30(30,2),
    //3-1小时
    hour1(60,3),
    //4-1天
    days1(1440,4),
    //5-1个月
    month1(43200,5);


    private int minutes;
    private int value;
    ESimLimitPeriod(int minutes, int value){
        this.minutes = minutes;
        this.value = value;
    }
    public int getMinutes() {
        return minutes;
    }
    public int getValue() {
        return value;
    }

    public static int getMinutesByValue(Integer value) {

        if(value!=null){
            for(ESimLimitPeriod e:ESimLimitPeriod.values()){
                if(e.getValue()==value){
                    return e.getMinutes();
                }
            }
        }

        return 0;
    }
}
