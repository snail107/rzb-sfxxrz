package com.zx.rzb.sfxxrz.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class TimeUtil {
	private static Logger log = Logger.getLogger(TimeUtil.class);
    /**
     * @Title: getCurrentTime
     * @Description: TODO(取当前时间yyyyMMdd)
     * @date 2012-11-15 下午06:06:58 
     * @author yang-lj
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getCurrentDate(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
    	return formatter.format(new Date());
    }
    
    /**
     * @Title: getHHmmssSS
     * @Description: TODO(获取时分秒毫秒)
     * @date 2013-9-5 下午06:01:27 
     * @author yang-lj
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
	public static String getHHmmssSS(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("HHmmssSS");
    	return formatter.format(new Date());
	}
	
	/**
     * @Title: getCurrentTimeMillis
     * @Description: TODO(取当前时间yyyyMMddHHmmssSS)
     * @date Mar 13, 2012 5:14:36 PM 
     * @author yang-lj
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getCurrentTimeMillis(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmssSS");
    	return formatter.format(new Date());
    }
    
    /**
     * @Title: getCurrentTime
     * @Description: TODO(取当前时间)
     * @date 2015年8月11日 下午2:01:48 
     * @author yang-lj
     * @param formatStr 格式化字符串如：yyyyMMddHHmmssSS
     * @return
     */
    public static String getCurrentTime(String formatStr){
    	SimpleDateFormat formatter = new SimpleDateFormat (formatStr);
    	return formatter.format(new Date());
    }

    /**
     * @Title: getCurrentTime
     * @Description: TODO(取当前时间yyyyMMddHHmmss)
     * @date 2012-11-15 下午06:06:58 
     * @author yang-lj
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getCurrentTime(){
    	SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
    	return formatter.format(new Date());
    }
    
    /**
     * @Title: validationTimeOut1
     * @Description: TODO(超时验证)
     * @date 2015年2月4日 下午5:22:53 
     * @author yang-lj
     * @param @param timeStr
     * @param @param computeTime
     * @param @return    设定文件
     * @return boolean    返回类型
     * @throws
     */
    public static boolean validationTimeOut1(String timeStr, String computeTime)throws Exception {
    	Calendar calendar = GregorianCalendar.getInstance();
    	calendar.setTime(TimeUtil.formatStringToDate(timeStr));
    	calendar.add(Calendar.MINUTE, Integer.valueOf(computeTime));
    	return compareSysTime(calendar.getTime());
    }
    
	/**
	 * @Title: formatStringToDate
	 * @Description: TODO(将“yyyyMMddHHmmss” 转换日期)
	 * @date 2012-11-23 下午12:59:07 
	 * @author yang-lj
	 * @param @param dateString
	 * @param @return    设定文件
	 * @return Date    返回类型
	 * @throws
	 */
	public static Date formatStringToDate(String dateString)throws ParseException{
		StringBuffer sb=new StringBuffer(dateString);
		sb.insert(4,"-");
		sb.insert(7,"-");
		sb.insert(10," ");
		sb.insert(13,":");
		sb.insert(16,":");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(sb.toString());
	}
	
	/**
	 * @Title: compareSysTime
	 * @Description: TODO(比较传入时间与系统时间)
	 * @date 2012-11-15 下午05:17:25 
	 * @author yang-lj
	 * @param @param datetime
	 * @param @return   datetime > systime retrun true;
	 * @return boolean    返回类型
	 * @throws
	 */
	public static boolean compareSysTime(Date datetime){
		if(datetime.getTime() > new Date().getTime() ){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @Title: comparSysDate
	 * @Description: TODO(比较日期,日期相等返回true,否则返回false)
	 * @date 2016年5月19日 上午10:12:55 
	 * @author yang-lj
	 * @param dataStr 
	 * @return
	 */
	public static boolean comparSysDate(String dataStr){
		int i=compareDateSupportEmpty(dataStr,getCurrentDate());
		if(i==0){
			return true;
		}else{
			return false;
		}
	}
    
	/**
	 * @Title: compareDateSupportEmpty
	 * @Description: TODO(比较两个日期字符串)
	 * @date 2013-11-7 上午10:06:40 
	 * @author yang-lj
	 * @param @param date1
	 * @param @param date2
	 * @param @return    设定文件
	 * @return int    返回类型  date1>date2 return 1 ,date1<date2 return -1,date1=date2 return 0
	 * @throws
	 */
	public static int compareDateSupportEmpty(String date1,String date2){
		date1=date1==null?"":date1;
		date2=date2==null?"":date2;
		if(date1.equals("") || date2.equals("")){
			if(date1.equals(date2)){
				return 0;
			}else if(date1.equals("")){
				return -1;
			}else if(date2.equals("")){
				return 1;
			}
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb1=new StringBuffer(date1);
		sb1.insert(4,"-");
		sb1.insert(7,"-");
		StringBuffer sb2=new StringBuffer(date2);
		sb2.insert(4,"-");
		sb2.insert(7,"-");
		try{
			Date dt1=format.parse(sb1.toString());
			Date dt2=format.parse(sb2.toString());
			if(dt1.getTime()>dt2.getTime()){
				return 1;
			}else if(dt1.getTime()<dt2.getTime()){
				return -1;
			}else{
				return 0;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @Title: getCurrentDaySurplusTime
	 * @Description: TODO(传出时间与当前时间的差，单位：秒)
	 * @date 2016年1月27日 下午2:29:36 
	 * @author yang-lj
	 * @param dateStr
	 * @return
	 */
	public static long getTimeDiff(String dateStr){
		long minute=0l;
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try{
			Date d1 = df.parse(getCurrentTime());
			Date d2 = df.parse(dateStr);
			long diff = d1.getTime() - d2.getTime();
//			long days = diff / (1000 * 60 * 60 * 24);
//			minute = diff / (1000 * 60);
			minute = diff / 1000;
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return minute;
	}
}
