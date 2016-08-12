package com.zx.rzb.sfxxrz.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.zx.rzb.sfxxrz.util.ConfigInfo;

/**
 * @Package com.zx.rzb.sfxxrz.mail
 * @Title: SimpleMailSender.java
 * @ClassName: SimpleMailSender
 * @Description: TODO(邮件发送)
 * @author yang-lj
 * @date 2016年5月5日 上午9:56:06
 * @version V1.0
 */
public class SimpleMailSender {
	private static JavaMailSenderImpl mailSender;

	public SimpleMailSender() {
		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(ConfigInfo.mailMap.get("host"));
		mailSender.setUsername(ConfigInfo.mailMap.get("user"));
		mailSender.setPassword(ConfigInfo.mailMap.get("pwd"));
	}

	public void simpleSend(String merchantName, String balance, String mailTo) {
		String msg = ConfigInfo.mailMap.get("template").toString();
		msg=String.format(msg, merchantName+"\n","你好！\n",balance);
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(mailSender.getUsername());
		smm.setTo(mailTo);
		smm.setCc(ConfigInfo.mailMap.get("cc"));
		smm.setSubject(ConfigInfo.mailMap.get("subject"));
		smm.setText(msg);
		mailSender.send(smm);
	}
}
