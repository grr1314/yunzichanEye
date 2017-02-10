package com.zchk.yunzichan.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
/**
 * ������һ��������
 * ����Ҫ�������ڻ�ȡ��Ļ�Ŀ�͸��Լ���λ֮���ת��
 * 
 * @author SenseTech
 *
 */
public class WidthHeight {
	public static int screenWidth;// ��Ļ���
	public static int screenHeight;// ��Ļ�߶�

	/**
	 * ȡ����Ļ�߿����ø������������ǰ��ص��ô˷���
	 * @param context
	 */
	public static void getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		screenHeight = outMetrics.heightPixels;
	}

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
