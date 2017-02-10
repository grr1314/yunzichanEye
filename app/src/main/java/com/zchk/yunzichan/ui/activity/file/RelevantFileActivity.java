package com.zchk.yunzichan.ui.activity.file;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.FileNameMessage;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.adapter.RelevantFileAdapter;

import java.util.ArrayList;
import java.util.List;

public class RelevantFileActivity extends BaseActivity implements
		OnItemClickListener {

	private RelevantFileAdapter myAdapter; // 数据适配器
	private List<FileNameMessage> data; // 数据实体
	private FileNameMessage[] fileNames = new FileNameMessage[6];

	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.relevant_file_layout);
		initView();
		putData();
	}

	private void initView() {
		initTopBar("相关文档", true, false, true);
		initTopBarListener(null, null, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listview = (ListView) findViewById(R.id.relevant_file_list);
		initDate();
		initListener();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(this);
	}

	private void initDate() {
		fileNames[0] = new FileNameMessage("史密斯品牌变压器维修手册");

		fileNames[1] = new FileNameMessage("史密斯品牌变压器FAQ手册");

		fileNames[2] = new FileNameMessage("西门子品牌变压器FAQ手册");

		fileNames[3] = new FileNameMessage("松下彩电维修手册");

		fileNames[4] = new FileNameMessage("螺杆式水冷冷水机维修手册");

		fileNames[5] = new FileNameMessage("螺杆式水冷冷水机自检手册");
	}

	private void putData() {
		data = new ArrayList<FileNameMessage>();
		for (int i = 0; i < fileNames.length; i++) {
			data.add(fileNames[i]);
		}
		myAdapter = new RelevantFileAdapter(this, data);

		listview.setAdapter(myAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(RelevantFileActivity.this,
				RelevantFileContentActivity.class);
		startActivity(intent);
		// 设置切换动画，从左边进入，右边退出
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

}
