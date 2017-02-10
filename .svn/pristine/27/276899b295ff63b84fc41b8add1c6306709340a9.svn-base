package com.zchk.yunzichan.ui.activity.query;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
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


public class CheckInfoByTempletelocalActivity extends Activity {

	private CheckSearchByTempleteAdapter myAdapter;
	private List<CheckSearchByTempleteManager> data;

	private String TableName;

	private Button check_templete_gaojiquery;
	private Button check_templete_shuaxin;
	private ListView check_templete_list;

	private CheckSearchByTempleteManager[] managers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.check_templete_local);

		Intent intent = getIntent();
		TableName = "Check_" + intent.getStringExtra("tableName") + "_RoleId";
		
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
		

		DatabaseHelper dbHelper = DBManage
				.getDatabaseHelper(CheckInfoByTempletelocalActivity.this);

		SQLiteDatabase database = dbHelper.getWritableDatabase();
		
		 String sql1 =
				 "insert into "+TableName+" (LabelId,RecordTime,CheckUser) values('6789','2016-02-02 12:23:22','mike')";
				
				 database.execSQL(sql1);

		String sql = "select * from " + TableName;
		Cursor cursor = database.rawQuery(sql, null);
		
		if (cursor.getCount() > 0) {

			cursor.moveToFirst();
			
			for (int i = 0; i < cursor.getCount(); i++) {
				String assetid = cursor.getString(cursor.getColumnIndex("LabelId"));
				String time = cursor.getString(cursor.getColumnIndex("RecordTime"));
				String checkUser = cursor.getString(cursor.getColumnIndex("CheckUser"));
				CheckSearchByTempleteManager manager = new CheckSearchByTempleteManager(assetid,time,checkUser);
				list.add(manager);
				
				cursor.moveToNext();
			}
		}
		
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
				"�豸ID","ʱ��","�����"));
		for (int i = 0; i < managers.length; i++) {
			data.add(new CheckSearchByTempleteManager(
					managers[i].deviceID, managers[i].time, managers[i].checkUser));
		}
		
		if (data.size()>0) {
			myAdapter = new CheckSearchByTempleteAdapter(CheckInfoByTempletelocalActivity.this, data);

			check_templete_list.setAdapter(myAdapter);
		}
	}

}
