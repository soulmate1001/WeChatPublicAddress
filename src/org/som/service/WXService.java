package org.som.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WXService {
	
	private static final String TOKEN = "abcd123456";

	/**
	 * ��֤ǩ��

	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
	public static boolean check(String timestamp, String nonce,String signature) {
		
		 // 1����token��timestamp��nonce�������������ֵ������� 
		String[] strs = new String[] {TOKEN,timestamp,nonce};
		Arrays.sort(strs);
		
		 // 2�������������ַ���ƴ�ӳ�һ���ַ�������sha1���� 
		String str = strs[0]+strs[1]+strs[2];
		String mysig = sha1(str);
		
		System.out.println(mysig);
		System.out.println(signature);
		
		 // 3�������߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		return mysig.equalsIgnoreCase(signature);
	}

	/**
	 * ����sha1����
	 */
	private static String sha1(String src) {
		try {
			
			//��ȡһ�����ܶ���
			MessageDigest md = MessageDigest.getInstance("sha1");
			//�����㷨
			byte[] digest = md.digest(src.getBytes());
			char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
			StringBuilder sb = new StringBuilder();
			//��������㷨
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
