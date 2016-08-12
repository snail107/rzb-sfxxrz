package com.zx.rzb.sfxxrz.mail;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import com.zx.rzb.sfxxrz.util.ConfigInfo;

public class SimpleMailSenderTest {
	
	@Before
	public void setUp() throws Exception {
		ConfigInfo.mailMap=new HashMap<String,String>();//邮件发送配置
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
					this.initNode(em, ConfigInfo.merchantKeyMap);
				}else if (em.attributeValue("name").equalsIgnoreCase("mail")){
					this.initNode(em, ConfigInfo.mailMap);
				}
			}
		} catch (DocumentException de) {
		} catch (Exception e) {
		}
		
	}
	
	@Test
	public void test() {
		SimpleMailSender ms=new SimpleMailSender();
		ms.simpleSend("影响力","100","10641136@qq.com");
	}
	
	private void initNode(Element em, Map<String, String> serviceMap) {
		List<?> list = em.elements();
		for (Iterator<?> sonit = list.iterator(); sonit.hasNext();) {
			Element sonem = (Element) sonit.next();
			serviceMap.put(sonem.attributeValue("id"), sonem.attributeValue("value"));
		}
	}

}
