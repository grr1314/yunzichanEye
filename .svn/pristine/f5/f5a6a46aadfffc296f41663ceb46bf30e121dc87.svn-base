package com.zchk.yunzichan.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.repair.AddRepairActivity;
import com.zchk.yunzichan.utils.DateUtils;

/**
 * ��̬�����������ݵ�Service
 * 
 * @author SenseTech
 * 
 */
public class CheckServerService extends Service {

	private static final String TAG = "CheckServerService";
	public static final String ACTION = "com.sensetech.yunzichanst.service.CheckServerService";
	private Notification mNotification;
	private NotificationManager mManager;

	int notification;

	private boolean isLastDayHas = false;// ��ʾ�Ƿ��Ѿ��������һ���֪ͨ
	private boolean isHalfMonthHas = false;// ��ʾ�Ƿ��Ѿ��������е�֪ͨ
	private boolean isLastDayBefore = false;

	// ��������
	private static final int Notifi_LoadDate = 0;// ��ʾ��������������
	private static final int Notifi_UpLoad = 1;// ��ʾ�ϴ���������

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// ��ʼ��Notification
		initNotification();

	}

	/**
	 * ��ʼ��Notification
	 */
	private void initNotification() {
		// TODO Auto-generated method stub
		// ��ʼ��Notification������
		mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotification = new Notification();
		int icon = R.mipmap.tubiao;
		// ����ͼ��
		mNotification.icon = icon;
		// ����״̬����ʾ����
		mNotification.tickerText = "Eά�ܼ�������Ϣ";
		// ����Ĭ�ϵ���ʾ����
		mNotification.defaults = Notification.DEFAULT_SOUND;
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
	}

	/**
	 * ��ʾNotification
	 */
	@SuppressWarnings("deprecation")
	private void showNotification(int notify) {
		mNotification.when = System.currentTimeMillis();
		String content = null;
		Intent intent = null;
		if (notify == Notifi_LoadDate) {
			content = "�������������ݣ��뼰ʱ���±������ݣ�";
			intent = new Intent(this, AddRepairActivity.class);
			notification = 0;
		} else {
			content = "�뼰ʱ�ϴ��������ݣ�";
			intent = new Intent(this, AddRepairActivity.class);
			notification = 1;
		}
		// ���õ��������Activity
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//				intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//		mNotification.setLatestEventInfo(this, getResources().getString(R.string.app_name), content, pendingIntent);
//		mManager.notify(notification, mNotification);
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		new PollingThread().start();
		new UploadThread().start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * Polling thread ģ����Server��ѯ���첽�߳�
	 * 
	 * @Author Ryan
	 * @Create 2013-7-13 ����10:18:34
	 */
	int count = 0;

	class PollingThread extends Thread {
		@Override
		public void run() {
			System.out.println("Polling...");
			count++;
			Log.e(TAG, "count:" + count);
			// �������ܱ�5����ʱ����֪ͨ
			if (count % 500 == 0) {
				showNotification(Notifi_LoadDate);
				// ���������������ڶ�Ӧ������ʾ���

				System.out.println("New message!");
			}
		}
	}

	/**
	 * �ύ�������ݵ��߳�
	 * 
	 * @author SenseTech
	 * 
	 */
//	int amount=0;
	class UploadThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// count++;
			Intent intent = new Intent(
					"com.sensetech.yunzichanst.receiver.UpdateUIReceiver");
			// ��ǰ�����Ǳ������һ���ʱ����ʾ
			if (DateUtils.getLastDay() == DateUtils.THE_LASTDAY
					&& !isLastDayHas) {
				Log.e(TAG, ""+DateUtils.getLastDay()+"="+DateUtils.THE_LASTDAY);
				showNotification(Notifi_UpLoad);
				// ��ʾ�Ժ󽫱������һ�����ʾ���Ϊtrue,��ֹService��ɱ�������û������洢��Щ����

			} else if (DateUtils.getLastDay() == DateUtils.THE_M_OFMONTH
					&& !isHalfMonthHas) {
				Log.e(TAG, "Other2");
				// ��ʾ����ÿ��15�ŵ�֪ͨ
				showNotification(Notifi_UpLoad);
				// ͬ��һ���ı�״̬
			} else if (!isLastDayBefore) {
				Log.e(TAG, "Other");
				// ��ʾ�������һ���ǰһ���֪ͨ
				showNotification(Notifi_UpLoad);
				// ͬ��һ���ı�״̬
				intent.putExtra("progress", 0);
				sendBroadcast(intent);
				isLastDayBefore=true;
			}
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
