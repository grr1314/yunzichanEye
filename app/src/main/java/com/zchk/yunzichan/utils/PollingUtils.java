package com.zchk.yunzichan.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * ��ѯ����
 * 
 * @author SenseTech
 * 
 */
public class PollingUtils {
	// ������ѯ����
	public static void startPollingService(Context context, int seconds,
			Class<?> cls, String action) {

		Log.e(action, "Start Polling");
		// ��ȡAlarmManagerϵͳ����
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// ��װ��Ҫִ�е�Service��Intent
		Intent intent = new Intent(context, cls);
		intent.setAction(action);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// ���������ʱ��
		long triggerAtTime = SystemClock.elapsedRealtime();
		Log.e("triggerAtTime", "ʱ�䣺" + triggerAtTime);

		// ʹ��AlarmManager��setRepeating�������ö���ִ�е�ʱ��������Ҫִ�е�Service
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime,
				seconds * 1000, pendingIntent);
	}

	// �ر���ѯ����
	public static void stopPollingService() {

	}
}
