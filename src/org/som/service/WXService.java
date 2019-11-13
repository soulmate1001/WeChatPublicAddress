package org.som.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WXService {
	
	private static final String TOKEN = "abcd123456";

	/**
	 * 验证签名

	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	public static boolean check(String timestamp, String nonce,String signature) {
		
		 // 1）将token、timestamp、nonce三个参数进行字典序排序 
		String[] strs = new String[] {TOKEN,timestamp,nonce};
		Arrays.sort(strs);
		
		 // 2）将三个参数字符串拼接成一个字符串进行sha1加密 
		String str = strs[0]+strs[1]+strs[2];
		String mysig = sha1(str);
		
		System.out.println(mysig);
		System.out.println(signature);
		
		 // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		return mysig.equalsIgnoreCase(signature);
	}

	/**
	 * 进行sha1加密
	 */
	private static String sha1(String src) {
		try {
			
			//获取一个加密对象
			MessageDigest md = MessageDigest.getInstance("sha1");
			//加密算法
			byte[] digest = md.digest(src.getBytes());
			char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
			StringBuilder sb = new StringBuilder();
			//处理加密算法
			for(byte b:digest) {
				sb.append(chars[(b >> 4)&15]);
				sb.append(chars[b&15]);
			}
			
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}

}
