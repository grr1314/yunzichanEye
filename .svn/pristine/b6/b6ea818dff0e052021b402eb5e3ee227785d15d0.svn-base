package com.zchk.yunzichan.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

/**
 * �й����ڲ������෽��
 * 
 * @author SenseTech
 * 
 */
public class DateUtils {
	public static final int THE_LASTDAY=0;//ÿ���µ����һ��
	public static final int THE_LASTDAYBEFORE=1;//ÿ���µĵ����ڶ���
	public static final int THE_M_OFMONTH=2;//ÿ�µ�15��
	
	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public static Date getDate() {
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(matter1.format(dt));
		return dt;
	}

	public static String getTime()
	{
		Date dt = new Date();
		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(matter1.format(dt));
		return matter1.format(dt);
	}
	/**
	 * �жϵ�ǰ�ǲ��Ǳ��µ����һ��
	 * @return
	 */
	public static int getLastDay() {
		Date a = new Date();
		Calendar b = Calendar.getInstance();
		b.setTime(a);
		int lastDay = b.getActualMaximum(Calendar.DAY_OF_MONTH);
		int now = b.get(Calendar.DAY_OF_MONTH);
		Log.e("TAG", "is the last day"+(now==lastDay));
		if (now==lastDay) {
			return THE_LASTDAY;
		}
		else if (now==lastDay-1) {
			return THE_LASTDAYBEFORE;
		}
		else if (now==15) {
			return THE_M_OFMONTH;
		}
		return -1;
	}

	/**
	 * �õ�����ǰ��ʱ��
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * �õ�������ʱ��
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
}
