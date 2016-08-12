package com.zx.rzb.sfxxrz.util;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
	
	/**
	 * @Title: initHmackey
	 * @Description: TODO(生成密钥)
	 * @date 2016年4月20日 下午3:42:00 
	 * @author yang-lj
	 * @return
	 * @throws Exception
	 */
	public static byte[] initHmackey()throws Exception{
		KeyGenerator keyGenerator=KeyGenerator.getInstance(Constants.MAC_ALGORITHM);
		SecretKey secretKey=keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * @Title: encodeHmac
	 * @Description: TODO(生成签名)
	 * @date 2016年4月20日 下午3:42:17 
	 * @author yang-lj
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeHmac(byte[] data,byte[] key)throws Exception{
		SecretKey secretKey=new SecretKeySpec(key,Constants.MAC_ALGORITHM);
		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	public static String md5(String text) {
		byte[] bts;
		try {
			bts = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bts_hash = md.digest(bts);
			StringBuffer buf = new StringBuffer();
			for (byte b : bts_hash) {
				buf.append(String.format("%02X", b & 0xff));
			}
			return buf.toString();
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
/*	@Test
	public void testMd5(){
		String aa="1|1|赵六|22|xx123l123nlooi|11111111";
		String bb="40664|50201|鲁婷婷|370704198602152428||CYQgEhbUKloEIarJDW4o";
		System.out.println(md5(aa).toLowerCase());
		System.out.println(md5(bb).toLowerCase());

//		1cdf2a7b6f6a70824e6bfeeee02e4cf4
//		e5aeb02e2df7e7de2709a8d682762da2
	}*/
	
/*	@Test
	public void testSign(){
		try {
			byte[] d1="对我摘要".getBytes("UTF-8");
			byte[] d2="对我摘要".getBytes("UTF-8");
			byte[] key=initHmackey();
			System.out.println(ToolUtil.bytes2hex(key));
//			byte[] key=ToolUtil.hex2byte("AB0E262281D4C04A847D0BB179D72C3E0BF771DBAD6FD4DE");
			
			byte[] data1=encodeHmac(d1,key);
			byte[] data2=encodeHmac(d2,key);
			System.out.println(ToolUtil.bytes2hex(data1));
			System.out.println(ToolUtil.bytes2hex(data2));
			
			Assert.assertArrayEquals(data1, data2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
