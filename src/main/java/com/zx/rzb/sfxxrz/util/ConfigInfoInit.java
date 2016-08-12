package com.zx.rzb.sfxxrz.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @Package com.allinpay.mobile.util
 * @Title: ConfigInfoInit.java
 * @ClassName: ConfigInfoInit
 * @Description: TODO(初始化各平台配置信息)
 * @author yang-lj
 * @date 2012-10-29 下午02:51:09
 * @version V1.0
 */
public class ConfigInfoInit extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ConfigInfoInit.class);

	public void init() throws ServletException {
		try {
			String filename="rzbConfig.xml";
			Document doc;
			if (new File(filename).exists()) {
				doc = new SAXReader().read(new FileInputStream(filename));
			} else {
				doc = new SAXReader().read(this.getClass().getResourceAsStream("/"+filename));
			}
			Element root = doc.getRootElement();
			List<?> el = root.elements();
			for (Iterator<?> it = el.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.attributeValue("name").equalsIgnoreCase("merchantKey")) {
					log.info("init merchantKey config ...");
					this.initNode(em, ConfigInfo.merchantKeyMap);
				}else if (em.attributeValue("name").equalsIgnoreCase("mail")){
					log.info("init mail config ...");
					this.initNode(em, ConfigInfo.mailMap);
				}else if (em.attributeValue("name").equalsIgnoreCase("channelFee")){
					log.info("init channelFee config ...");
					this.initNode(em, ConfigInfo.channelFeeMap);
				}
			}
		} catch (DocumentException de) {
			log.error(" ConfigInfoInit err:" + de.getMessage(),de);
		} catch (Exception e) {
			log.error(" ConfigInfoInit err:" + e.getMessage(),e);
		}
	}

	/**
	 * @Title: initMap
	 * @Description: TODO(初始化业务MAP)
	 * @date 2012-11-14 下午03:15:50
	 * @author yang-lj
	 * @param @param em
	 * @param @param serviceMap 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initNode(Element em, Map<String, String> serviceMap) {
		List<?> list = em.elements();
		for (Iterator<?> sonit = list.iterator(); sonit.hasNext();) {
			Element sonem = (Element) sonit.next();
			serviceMap.put(sonem.attributeValue("id"), sonem.attributeValue("value"));
		}
	}
}
