package com.zchk.yunzichan.ui.fragment.leader;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.check.CheckMaintenanceSearchTwoManager;
import com.zchk.yunzichan.entity.model.query.AdminMaintainDeviceInfoMessage;
import com.zchk.yunzichan.entity.model.query.MaintainDevicesInfo;
import com.zchk.yunzichan.ui.activity.query.CheckAndMaintenanceQueryActivity;
import com.zchk.yunzichan.ui.adapter.Check_Maintenance_AdapterTwo;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 *
 * @author SenseTech
 * 
 */
public class MaintenanceQueryFragment extends Fragment {

	private static String url = GlobalDefine.CmdPath.cmdPath
			+ "ManagerMaintainInfoSearchCmd";

	private View view;
	private Check_Maintenance_AdapterTwo myAdapter;
	private List<CheckMaintenanceSearchTwoManager> data;

	private AdminMaintainDeviceInfoMessage persons;

	public String shuaxinJson;
	public String JsonString;
	private ListView check_list; // ListView

	public String[] deviceIds;
	public String[] times;
	public String[] notes;
	public String[] accounts;

	private ViewStub vs_error;

	private CheckAndMaintenanceQueryActivity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.check_maintenance_layouttwo,
				container, false);
		activity = (CheckAndMaintenanceQueryActivity) getActivity();
		initView();
		initData();
		return view;
	}

	private void initData() {
		AdminMaintainDeviceInfoMessage userCheckrequ = new AdminMaintainDeviceInfoMessage();
		userCheckrequ.items = new MaintainDevicesInfo[1];
		userCheckrequ.sum = "10";
		userCheckrequ.userName = activity.getUser().getAccount();
		String param = JsonTools.StringToJson_AdminMaintenance(userCheckrequ);
		System.out.println("param JsonStr:" + param);

		OkHttpUtils.postString().url(url).content(param).build()
				.execute(new StringCallback() {

					@Override
					public void onAfter(int code) {
						// TODO Auto-generated method stub
						super.onAfter(code);
						activity.FragmentDismissDialog();
					}

					@Override
					public void onBefore(Request request,int code) {
						// TODO Auto-generated method stub
						super.onBefore(request,code);
						activity.FragmentShowDialog();
					}

					@Override
					public void onResponse(String response,int code) {
						// TODO Auto-generated method stub
						putData(response);

					}

					@Override
					public void onError(Call call, Exception e,int code) {
						// TODO Auto-generated method stub
						showFragmentView();
					}
				});

	}

	protected void showFragmentView() {
		check_list.setVisibility(View.GONE);
		vs_error = (ViewStub) view.findViewById(R.id.vs_error);
		vs_error.inflate();
		TextView tv_error = (TextView) view.findViewById(R.id.tv_error);
		tv_error.setText("网络错误");
	}

	private void initView() {
		check_list = (ListView) view
				.findViewById(R.id.check_maintenance_listtwo);
	}

	private void putData(String JsonString) {
		persons = (AdminMaintainDeviceInfoMessage) JsonTools.JsonToStruts(
				JsonString, AdminMaintainDeviceInfoMessage.class);
		if (persons.items == null) {
			ToastUtil.showToast(getActivity(), "查询失败");
			showFragmentView();
			return;
		}

		if (persons.items.length == 0) {
			return;
		}

		deviceIds = new String[persons.items.length];
		times = new String[persons.items.length];
		notes = new String[persons.items.length];
		accounts = new String[persons.items.length];

		for (int i = 0; i < persons.items.length; i++) {
			deviceIds[i] = persons.items[i].deviceName;
			times[i] = persons.items[i].checkTime;
			notes[i] = persons.items[i].repairInfo;
			accounts[i] = persons.items[i].maintainUser;
		}

		getData();
	}

	private void getData() {
		data = new ArrayList<CheckMaintenanceSearchTwoManager>();
		for (int i = 0; i < deviceIds.length; i++) {
			data.add(new CheckMaintenanceSearchTwoManager(deviceIds[i],
					times[i], notes[i], accounts[i]));
		}
		myAdapter = new Check_Maintenance_AdapterTwo(getActivity(), data);
		check_list.setAdapter(myAdapter);
	}

}
