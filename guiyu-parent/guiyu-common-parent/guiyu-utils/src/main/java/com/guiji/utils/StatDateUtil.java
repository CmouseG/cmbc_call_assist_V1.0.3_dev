package com.guiji.utils;


import com.guiji.common.model.DateRange;

import java.util.Date;

/** 
* @ClassName: StatDateUtil 
* @Description: 统计类日期工具类
* @author: weiyunbo
* @date 2018年7月9日 下午3:01:13 
* @version V1.0  
*/
public class StatDateUtil {

	/**
	 * 根据日期类型，获取日期范围
	 * @param dateType
	 * @return
	 */
	public static DateRange getDateStatRange(String dateType) {
		DateRange dateRange = new DateRange();
		if(DateTeypEnum.TODAY.getDateType().equals(dateType)) {
			//当天
			String today = DateUtil.getTodateString();
			dateRange.setBeginDate(today);
			dateRange.setEndDate(today);
			return dateRange;
		}else if(DateTeypEnum.YESTERDAY.getDateType().equals(dateType)) {
			//昨天
			String yestoday = DateUtil.getYesterdayYYYYMMDD();
			dateRange.setBeginDate(yestoday);
			dateRange.setEndDate(yestoday);
			return dateRange;
		}else if(DateTeypEnum.WEEK.getDateType().equals(dateType)) {
			//本周
			String today = DateUtil.getTodateString();
			dateRange.setBeginDate(DateUtil.getMondayOfDate(today)); //本周一
			dateRange.setEndDate(DateUtil.getSundayOfDate(today)); //本周日
			return dateRange;
		}else if(DateTeypEnum.LAST_WEEK.getDateType().equals(dateType)) {
			//上周
			//获取上周今天
			Date date = new Date(System.currentTimeMillis() - DateUtil.DATEMM  * 7 * 1000L);     //7天前
			String lastWeekToday =DateUtil.getYyyyMMddHH_NOT_().format(date);
			dateRange.setBeginDate(DateUtil.getMondayOfDate(lastWeekToday)); //上周一
			dateRange.setEndDate(DateUtil.getSundayOfDate(lastWeekToday)); //上周日
			return dateRange;
		}else if(DateTeypEnum.MONTH.getDateType().equals(dateType)) {
			//本月
			String today = DateUtil.getTodateString(); //今天
			dateRange.setBeginDate(DateUtil.getMonthStartDate(today)); //本月第一天
			dateRange.setEndDate(DateUtil.getMonthEndDate(today)); //本月最后一天
			return dateRange;
		}else if(DateTeypEnum.LAST_MONTH.getDateType().equals(dateType)) {
			//上月
			dateRange.setBeginDate(DateUtil.getBeforeFirstMonthdate()); //上月第一天
			dateRange.setEndDate(DateUtil.getBeforeLastMonthdate()); //上月最后一天
			return dateRange;
		}else if(DateTeypEnum.MONTH_1.getDateType().equals(dateType)) {
			//1月份
			return getMontyDayRange(1);
		}else if(DateTeypEnum.MONTH_2.getDateType().equals(dateType)) {
			//2月份
			return getMontyDayRange(2);
		}else if(DateTeypEnum.MONTH_3.getDateType().equals(dateType)) {
			//3月份
			return getMontyDayRange(3);
		}else if(DateTeypEnum.MONTH_4.getDateType().equals(dateType)) {
			//4月份
			return getMontyDayRange(4);
		}else if(DateTeypEnum.MONTH_5.getDateType().equals(dateType)) {
			//5月份
			return getMontyDayRange(5);
		}else if(DateTeypEnum.MONTH_6.getDateType().equals(dateType)) {
			//6月份
			return getMontyDayRange(6);
		}else if(DateTeypEnum.MONTH_7.getDateType().equals(dateType)) {
			//7月份
			return getMontyDayRange(7);
		}else if(DateTeypEnum.MONTH_8.getDateType().equals(dateType)) {
			//8月份
			return getMontyDayRange(8);
		}else if(DateTeypEnum.MONTH_9.getDateType().equals(dateType)) {
			//9月份
			return getMontyDayRange(9);
		}else if(DateTeypEnum.MONTH_10.getDateType().equals(dateType)) {
			//10月份
			return getMontyDayRange(10);
		}else if(DateTeypEnum.MONTH_11.getDateType().equals(dateType)) {
			//11月份
			return getMontyDayRange(11);
		}else if(DateTeypEnum.MONTH_12.getDateType().equals(dateType)) {
			//12月份
			return getMontyDayRange(12);
		}else if(DateTeypEnum.QUARTER_1.getDateType().equals(dateType)) {
			//一季度
	        dateRange.setBeginDate(DateUtil.getQuarterBeginDate(1)); //1季度第一天
			dateRange.setEndDate(DateUtil.getQuarterEndDate(1)); //1季度最后一天
			return dateRange;
		}else if(DateTeypEnum.QUARTER_2.getDateType().equals(dateType)) {
			//二季度
			dateRange.setBeginDate(DateUtil.getQuarterBeginDate(2)); //2季度第一天
			dateRange.setEndDate(DateUtil.getQuarterEndDate(2)); //2季度最后一天
			return dateRange;
		}else if(DateTeypEnum.QUARTER_3.getDateType().equals(dateType)) {
			//三季度
			dateRange.setBeginDate(DateUtil.getQuarterBeginDate(3)); //3季度第一天
			dateRange.setEndDate(DateUtil.getQuarterEndDate(3)); //3季度最后一天
			return dateRange;
		}else if(DateTeypEnum.QUARTER_4.getDateType().equals(dateType)) {
			//四季度
			dateRange.setBeginDate(DateUtil.getQuarterBeginDate(4)); //4季度第一天
			dateRange.setEndDate(DateUtil.getQuarterEndDate(4)); //4季度最后一天
			return dateRange;
		}else if(DateTeypEnum.HALF_YEAR_1.getDateType().equals(dateType)) {
			//上半年
			String today = DateUtil.getTodateString();
			String day = today.substring(0, 4) +"0228";
	        dateRange.setBeginDate(DateUtil.getHalfYearStartTime(day)); //上半年第一天
			dateRange.setEndDate(DateUtil.getHalfYearEndTime(day)); //上半年最后一天
			return dateRange;
		}else if(DateTeypEnum.HALF_YEAR_2.getDateType().equals(dateType)) {
			//下半年
			String today = DateUtil.getTodateString();
			String day = today.substring(0, 4) +"0709";
	        dateRange.setBeginDate(DateUtil.getHalfYearStartTime(day)); //上半年第一天
			dateRange.setEndDate(DateUtil.getHalfYearEndTime(day)); //上半年最后一天
			return dateRange;
		}else if(DateTeypEnum.YEAR.getDateType().equals(dateType)) {
			//全年
			String today = DateUtil.getTodateString();
			dateRange.setBeginDate(today.substring(0, 4)+"0101");
			dateRange.setEndDate(today.substring(0, 4)+"1231");
			return dateRange;
		}
		return null;
	}
	
	/** 
	    * @ClassName: DateTeypEnum 
	    * @Description: 日期分类
	    * @author: weiyunbo
	    * @date 2018年7月9日 下午15:04:46 
	    * @version V1.0  
	    */
	    public enum DateTeypEnum {
	    	TODAY("TODAY","今天"),
	    	YESTERDAY("YESTERDAY","昨天"),
	    	WEEK("WEEK","本周"),
	    	LAST_WEEK("LAST_WEEK","上周"),
	    	MONTH("MONTH","本月"),
	    	LAST_MONTH("LAST_MONTH","上月"),
	    	MONTH_1("MONTH_1","一月份"),
	    	MONTH_2("MONTH_2","二月份"),
	    	MONTH_3("MONTH_3","三月份"),
	    	MONTH_4("MONTH_4","四月份"),
	    	MONTH_5("MONTH_5","五月份"),
	    	MONTH_6("MONTH_6","六月份"),
	    	MONTH_7("MONTH_7","七月份"),
	    	MONTH_8("MONTH_8","八月份"),
	    	MONTH_9("MONTH_9","九月份"),
	    	MONTH_10("MONTH_10","十月份"),
	    	MONTH_11("MONTH_11","十一月份"),
	    	MONTH_12("MONTH_12","十二月份"),
	    	QUARTER_1("QUARTER_1","一季度"),
	    	QUARTER_2("QUARTER_2","二季度"),
	    	QUARTER_3("QUARTER_3","三季度"),
	    	QUARTER_4("QUARTER_4","四季度"),
	    	HALF_YEAR_1("HALF_YEAR_1","上半年"),
	    	HALF_YEAR_2("HALF_YEAR_2","下半年"),
	    	YEAR("YEAR","全年");
	    	//返回码
	        private String dateType;  
	        //返回信息
	        private String dateTypeName;
	        
	        private DateTeypEnum(String dateType, String dateTypeName) {
	            this.dateType = dateType;  
	            this.dateTypeName = dateTypeName;  
	        } 
			/**
			 * @return the dateType
			 */
			public String getDateType() {
				return dateType;
			}
			/**
			 * @param dateType the dateType to set
			 */
			public void setDateType(String dateType) {
				this.dateType = dateType;
			}
			/**
			 * @return the dateTypeName
			 */
			public String getDateTypeName() {
				return dateTypeName;
			}
			/**
			 * @param dateTypeName the dateTypeName to set
			 */
			public void setDateTypeName(String dateTypeName) {
				this.dateTypeName = dateTypeName;
			}  
	    }
	    
	    /**
	     * 获取某年某月
	     * @param month
	     * @return
	     */
	    private static DateRange getMontyDayRange(int month) {
	    	DateRange dateRange = new DateRange();
	    	String today = DateUtil.getTodateString();
	    	int year = Integer.parseInt(today.substring(0, 4));
	    	dateRange.setBeginDate(DateUtil.getFirstDayOfMonth1(year,month)); //某月第一天
			dateRange.setEndDate(DateUtil.getLastDayOfMonth1(year,month)); //某月最后一天
			return dateRange;
	    }
}
