package com.zchk.yunzichan.ui.fragment.main;

import java.io.File;
import java.util.List;
import java.util.Map;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.entity.model.appupdate.AppUpdateRequest;
import com.zchk.yunzichan.entity.model.appupdate.AppUpdateResponse;
import com.zchk.yunzichan.ui.activity.main.MainActivity;
import com.zchk.yunzichan.ui.activity.system.AboutUsActivity;
import com.zchk.yunzichan.ui.activity.system.TextSetActivity;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public class System_Fragment extends Fragment {
	private RelativeLayout rl_about_us;
	private RelativeLayout rl_text_type;
	private RelativeLayout rl_edition;
	private DBUtils dbu;
	private MainActivity activity;
	List<Map<String, Object>> ls;
	private PackageManager pm;
	private int versionCode;
	private String versionName;
	private TextView tv_version;
	private static String url = GlobalDefine.CmdPath.cmdPath
			+ "APPApkCheckByVersionCmd";//版本检测
	private AppUpdateResponse appUpdateResponse;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (MainActivity) getActivity();
		View view = inflater.inflate(R.layout.tab_system, container, false);
		initViews(view);
		getVersonInfo();//得到当前的版本号和版本名称
		return view;
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		rl_about_us = (RelativeLayout) view.findViewById(R.id.rl_about_us);
		rl_text_type = (RelativeLayout) view.findViewById(R.id.rl_text_type);
		rl_edition = (RelativeLayout) view.findViewById(R.id.rl_edition);
		tv_version = (TextView) view.findViewById(R.id.tv_version);
		rl_about_us.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), AboutUsActivity.class);
				startActivity(intent);
			}
		});

		rl_text_type.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), TextSetActivity.class);
				startActivity(intent);
			}
		});

		//点击版本更新，进行版本检测
		rl_edition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//进行联网操作，检测版本更新
				AppUpdateRequest appUpdateRequest = new AppUpdateRequest();
				appUpdateRequest.userAccountName = "";
				final String param = JsonTools.StringToJson_AppUpdate(appUpdateRequest);
				//实际调用的是BaseActivity内的方法，请求网络数据
				activity.http(url, 1, param, new StringCallback() {

					@Override
					public void onAfter(int code) {
						super.onAfter(code);
					}
					@Override
					public void onBefore(Request request, int code) {
						super.onBefore(request, code);
					}
					@Override
					public void onResponse(String response, int code) {
						Log.i("csh", "onResponse:版本检测"+response);
						Gson gson = new Gson();
						appUpdateResponse = gson.fromJson(response, AppUpdateResponse.class);
						if(versionName.equals(appUpdateResponse.version)){//模拟数据，如果当前版本小于1.2，进行下载更新（需要版本号，和下载的url）
							Toast.makeText(getActivity(),"当前已是最新版本",Toast.LENGTH_LONG).show();
						}else {
							showUpdateDialog("检测到新的版本，请及时更新！","");
						}
					}

					@Override
					public void onError(Call call, Exception e, int code) {
						// show error page
						ToastUtil.showToast(getActivity(), "网络错误，请检查网络状态或稍后重试！");
						e.printStackTrace();
					}
				});


			}
		});
	}
	private void getVersonInfo() {
		pm = getActivity().getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = pm.getPackageInfo(getActivity().getPackageName(), 0);
			versionCode = packageInfo.versionCode;
			versionName = packageInfo.versionName;
			tv_version.setText(versionName+"版");
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void showUpdateDialog(final String msg, final String apkurl) {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("更新提示");
				builder.setMessage(msg);
				// 点击取消后直接跳到主界面
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DownLoadApk(GlobalDefine.CmdPath.apkPath+appUpdateResponse.apkUrl);
					}
				});
				// 设置一个对话框消失的监听
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub

					}
				});
				// 设置对话框不能取消 只能点击两个按钮其一
				// builder.setCancelable(false);
				builder.show();
			}
		});

	}
	private void DownLoadApk(String uri) {
		OkHttpUtils
				.get()
				.url(uri)
				// .tag("LoadingActivity")
				.build()//
				.execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "yunzichan.Apk")//
				{
					private long total1;
					private float progress1;
					//					private NotificationManager updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					@SuppressWarnings("unused")
					@Override
					public void onError(Call arg0, Exception arg1, int arg2) {
//						// TODO Auto-generated method stub
						arg1.printStackTrace();
						if(pd!=null){
							pd.dismiss();
						}
						Log.d("jjddnnn","onError");
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setTitle("温馨提示");
								builder.setMessage("           下载出错！！");
								builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								});
								// 设置一个对话框消失的监听
								builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {
										// TODO Auto-generated method stub

									}
								});
								// 设置对话框不能取消 只能点击两个按钮其一
								// builder.setCancelable(false);
								builder.show();
							}
						});
					}
					public void onForwardToAccessibility(View view) {
						Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
						startActivity(intent);
					}

					@Override
					public void onResponse(File file, int arg1) {
						Intent intent = new Intent();
						intent.setAction("android.intent.action.VIEW");
						// 翻译一个file 成Uri
						Uri uri = Uri.fromFile(file);
						// 如果需要同时设置type和data需要 同时设置
						intent.setDataAndType(uri,"application/vnd.android.package-archive");
						startActivityForResult(intent, REQUEST_FOR_INSTALL_APK);
//						System.exit(0);
					}

					@Override
					public void onBefore(Request request, int id) {
						super.onBefore(request, id);
						pd = new ProgressDialog(getActivity());
						pd.setTitle("下载进度");
						// 设置对话框的样式为水平进度条
						pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						// 对话框显示出来
						pd.setOnKeyListener(new DialogInterface.OnKeyListener() {

							@Override
							public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
								if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction()== KeyEvent.ACTION_DOWN) {
									long secondTime = System.currentTimeMillis();
									if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
										firstTime = secondTime;//更新firstTime
										return true;
									} else {
//										finish();
										System.exit(0);
										//  OkHttpUtils.getInstance().cancelTag("LoadingActivity");
										///  UIUtils.deleteFile(MakerApplication.getInstance().getFilePath());
										// MakerApplication.getInstance().exitApp();
									}
								}
								return true;
							}
						});
						pd.show();
					}

					@Override
					public void inProgress(final float progress, final long total, int id) {
						super.inProgress(progress, total, id);
						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {
								pd.setMax((int) total/(1024));
								pd.setProgress( (int) ( progress*(int) total/(1024)));
							}
						});
					}

					@Override
					public void onAfter(int id) {
						super.onAfter(id);
						if (pd != null) {
							// 下载完成后 对话框 消失
							pd.dismiss();
						}
					}
				});
	}
	long firstTime = 0;
	protected static final int REQUEST_FOR_INSTALL_APK = 1;
	private ProgressDialog pd;
}