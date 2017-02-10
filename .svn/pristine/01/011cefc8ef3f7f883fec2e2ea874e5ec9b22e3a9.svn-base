package com.zchk.yunzichan.receiver;

;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.util.Log;

import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.DialogUtil;
import com.zchk.yunzichan.utils.NetWorkUtils;


/**
 * 检测网络状态
 *
 * @author SenseTech
 *
 */
public class NetWorkCheckReceiver extends BroadcastReceiver {

	int type;
	boolean isDone=false;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		type = NetWorkUtils.getAPNType(context);
		switch (type) {
			case 0:
				// 没有网
				// 通过对话框询问是否进入离线状态
				if (share.getMode(context)== Constant.NETWORKONLINE) {
					DialogUtil.showDialog(context,"当前处于无网络连接状态，是否进入离线状态",Constant.NETWORKONLINE);
				}
				Log.e("NetWorkCheckReceiver", "NetWorkCheckReceiver");
				break;
			case 1:
				// wifi
			case 2:
				// 3G
			case 3:
				// 2G
			case 4:
				// 4G
				if (share.getMode(context)==Constant.NETWORKOFF) {
					DialogUtil.showDialog(context,"当前处于网络连接状态，是否进入在线状态",Constant.NETWORKOFF);
				}
				break;

			default:
				break;
		}
	}
}
