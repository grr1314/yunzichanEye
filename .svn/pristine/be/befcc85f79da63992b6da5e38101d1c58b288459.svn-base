package com.zchk.yunzichan.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.minterface.DialogCallBack;
import com.zchk.yunzichan.ui.view.MyDialog;


public class DialogUtil {
	static AlertDialog dialog;
	static MyDialog myDialog;
//	static MyDialogWithoutBtn;

	/**
	 * 显示对话框
	 *
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void showDialog(final Context context, final String content,
								  final int type) {
		// TODO Auto-generated method stub
		MyApplication application;
		AlertDialog.Builder builder = new Builder(context,
				ProgressDialog.THEME_HOLO_LIGHT);
		// 设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		// 设置对话框标题
		builder.setTitle("提示");
		// 设置对话框内的文本
		builder.setMessage(content);
		// 设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行点击确定按钮的业务逻辑
				if (type == Constant.NETWORKONLINE) {
					// 利用缓存记录模式,缓存记录为离线状态
					share.setMode(context, Constant.NETWORKOFF);
				} else {
					share.setMode(context, Constant.NETWORKONLINE);
				}

			}
		});

		// 设置取消按钮
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行点击取消按钮的业务逻辑
				dialog.dismiss();
			}
		});
		// 使用builder创建出对话框对象
		AlertDialog dialog = builder.create();
		// 显示对话框
		dialog.show();
	}

	/**
	 * 显示对话框
	 *
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void showDialog(final Context context, final String content,
								  final DialogCallBack call) {
		// TODO Auto-generated method stub
		MyApplication application;
		AlertDialog.Builder builder = new Builder(context,
				ProgressDialog.THEME_HOLO_LIGHT);
		// 设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		// 设置对话框标题
		builder.setTitle("提示");
		// 设置对话框内的文本
		builder.setMessage(content);
		// 设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行点击确定按钮的业务逻辑
				call.sure();

			}
		});
		// 设置取消按钮
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行点击取消按钮的业务逻辑
				call.cancel();

			}
		});
		// 使用builder创建出对话框对象
		dialog = builder.create();
		// 显示对话框
		dialog.show();
	}

	@SuppressWarnings("unused")
	public static void showMyDialog(final Context context,
									final String content, final DialogCallBack call) {
		MyDialog.Builder bulider = new MyDialog.Builder(context);
		bulider.setMessage(content);
		bulider.setTitle("提示");
		bulider.setNegativeButton("好的", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				call.sure();
			}
		});
		bulider.setPositiveButton("不要", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				call.cancel();
			}
		});
		myDialog = bulider.create();
		myDialog.show();
	}

	public static void dismissDialog() {
		dialog.dismiss();
	}
	public static void dismissMyDialog() {
		myDialog.dismiss();
	}
}
