package com.zchk.yunzichan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * ���һЩ�ַ����Ĵ��������Լ�������ʽ
 * 
 * @author SenseTech
 * 
 */
public class StringUtils {

	private static final String TAG="StringUtils";
	/**
	 * 检查手机号的正则表达式
	 */
	public static boolean checkPhone(String str)
	{
		
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$");  

		Matcher m = p.matcher(str);  

		return m.matches(); 
	}
	/**
	 * 分割字符串
	 * @param str
	 * @return
	 */
	public static String cutString(String str)
	{
		Log.e(TAG, "str="+str);
		if (str.length()==25) {
			return str;
		}
		String[] s=str.split("=");
		int length=s.length;
		return s[length-1];
	}
	
	public static String cutString2(String str)
	{
		String[] s=str.split("=");
		int length=s.length;
		return s[length-1];
	}
	public static String cutPicTime(String str)
	{
		String string=str.replace("-","");
		String str2=string.replace(" ","");
		String str3=str2.replace(":","");
		return str3;
	}
	public static String spiltTime(String str)
	{
		String[] s=str.split(".");
		int length=s.length;
		return s[length-1];
	}

	public static String splitDeviceName(String name)
	{
		if (name.isEmpty())
		{
			return null;
		}
		String[] s=name.split("%");
		int length=s.length;
		return s[0];
	}
}
