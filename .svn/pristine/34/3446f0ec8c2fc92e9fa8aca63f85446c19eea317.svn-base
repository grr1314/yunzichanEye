package com.zchk.yunzichan.ui.activity.query;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.db.DBManage;
import com.zchk.yunzichan.db.DatabaseHelper;
import com.zchk.yunzichan.entity.bean.check.CheckSearchByTempleteManager;
import com.zchk.yunzichan.ui.adapter.CheckSearchByTempleteAdapter;


public class MaintenanceInfoByTempletelocalActivity extends Activity {

	private CheckSearchByTempleteAdapter myAdapter; // 数据适配器
	private List<CheckSearchByTempleteManager> data; // 数据实体

	private String TableName;

	private Button check_templete_gaojiquery;
	private Button check_templete_shuaxin;
	private ListView check_templete_list;

	private CheckSearchByTempleteManager[] managers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.check_templete_local);

		TableName = "equipmentMaintainRecord";

		Log.i("aasd",TableName);

		initView();
		initEven();
	}

	public void initView() {
		check_templete_gaojiquery = (Button) findViewById(R.id.check_templete_gaojiquery);
		check_templete_shuaxin = (Button) findViewById(R.id.check_templete_shuaxin);
		check_templete_list = (ListView) findViewById(R.id.check_templete_list);
		getData();
	}

	public void initEven() {

		check_templete_shuaxin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getData();
			}
		});
	}

	private void getDataBySql() {

		LinkedList list = new LinkedList();

		// 创建一个DatabaseHelper对象
		// 取得一个只读的数据库对象
		DatabaseHelper dbHelper = DBManage
				.getDatabaseHelper(MaintenanceInfoByTempletelocalActivity.this);
		// 取得对sqlite的操作
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		String sql = "select * from equipmentMaintainRecord";
		Cursor cursor = database.rawQuery(sql, null);

		Log.i("asda", cursor.getCount()+"");

		if (cursor.getCount() > 0) {
			// 必须使用moveToFirst方法将记录指针移动到第1条记录的位置
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {

				Log.i("asda", "sssssssssssss"+i);
				String assetid = cursor.getString(cursor.getColumnIndex("assetCode"));
				String time = cursor.getString(cursor.getColumnIndex("checkTime"));
				String checkUser = cursor.getString(cursor.getColumnIndex("checkUser"));
				CheckSearchByTempleteManager manager = new CheckSearchByTempleteManager(assetid,time,checkUser);
				list.add(manager);

				cursor.moveToNext();
			}
		}
		cursor.close();

		database.close();

		Log.i("tran", "list.size():" + list.size());
		if (list.size()>0)
		{
			managers = (CheckSearchByTempleteManager[])list.toArray(new CheckSearchByTempleteManager[list.size()]);
		}
	}

	private void getData() {

		data = new ArrayList<CheckSearchByTempleteManager>();

		if (managers == null || managers.length<=0)
		{
			getDataBySql();
		}


		if (managers == null || managers.length<=0)
		{
			return ;
		}

		data.add(new CheckSearchByTempleteManager(
				"设备ID","时间","维保人"));
		for (int i = 0; i < managers.length; i++) {
			data.add(new CheckSearchByTempleteManager(
					managers[i].deviceID, managers[i].time, managers[i].checkUser));
		}

		if (data.size()>0) {
			myAdapter = new CheckSearchByTempleteAdapter(MaintenanceInfoByTempletelocalActivity.this, data);

			check_templete_list.setAdapter(myAdapter);
		}
	}

}
