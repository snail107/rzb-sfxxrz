package com.zx.rzb.sfxxrz.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.zx.rzb.sfxxrz.mail.SimpleMailSender;
import com.zx.rzb.sfxxrz.mybatis.entity.MerchantInfo;
import com.zx.rzb.sfxxrz.mybatis.mapper.MerchantInfoMapper;

public class TimedMail {
	private static Logger log = Logger.getLogger(TimedMail.class);
	private int count = 0; // 任务执行次数
	
	@Inject
	private MerchantInfoMapper mim;

	public TimedMail() {
		log.info("----rzb--TimedMail--定时检查商户余额，发送告警邮件...");
		new SecondTimer();
	}

	/**
	 * @Package com.allinpay.mobile.ipos.service
	 * @Title: RetrivlRefNum.java
	 * @ClassName: SecondTimer
	 * @Description: TODO(第一次后执行)
	 * @author yang-lj
	 * @date 2016年1月19日 下午3:19:23
	 * @version V1.0
	 */
	class SecondTimer extends java.util.TimerTask {
		public SecondTimer() {
			// 设置执行时间
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH); 		// 每天
			// int hour = calendar.get(Calendar.HOUR_OF_DAY); 	// 每小时
			// int minute = calendar.get(Calendar.MINUTE); 		// 每分钟
			// int second = calendar.get(Calendar.SECOND); 		// 每秒

			// 定制每天的00:00:00执行，
			calendar.set(year, month, day + 1, 00, 00, 00); // 任务执行的开始时间
			// calendar.set(year, month, day, hour, minute, second+10); //
			// 任务执行的开始时间,当前时间10秒后开始执行
			Date date = calendar.getTime();
			Timer timer = new Timer();
			int period = 1000 * 60 * 60 * 24; // 任务执行间隔时间
			// int period = 10000; //任务执行间隔时间

			/**
			 * 每天的date时刻执行task，每隔1天重复执行 任务启动的时候会先被调用一次!!!------------NO!
			 * 当date小于当前系统时间时，会马上执行，间隔时间period也从当前时间开始计算
			 */
			// timer.schedule(this, date, period);

			timer.scheduleAtFixedRate(this, date, period);// 固定间隔时间执行？？
		}

		public void run() {
			++count;
			getMerchantInfo();
			log.info("----rzb--TimedMail--Timer----时间=" + new Date() + " 执行了" + count + "次 ");
		}
	}
	
	
	/**
	 * @Title: getMerchantInfo
	 * @Description: TODO(取商户信息，对余额小于告警金额的发送邮件)
	 * @date 2016年5月18日 下午4:06:55 
	 * @author yang-lj
	 */
	private void getMerchantInfo(){
		SimpleMailSender mailSend=new SimpleMailSender();
		List<MerchantInfo> list=mim.queryAllMerchantInfo();
		if(null!=list && !list.isEmpty()){
			for(int i=0,j=list.size();i<j;i++){
				MerchantInfo mi=list.get(i);
				if(mi.getBalance()<mi.getWarning()){
					mailSend.simpleSend(mi.getMerchName(),mi.getBalance()+"",mi.email);//邮件通知商户
				}
			}
		}
	}

}
