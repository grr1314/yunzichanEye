package com.zchk.yunzichan.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.file.RelevantFileActivity;


/**
 * 知识库的主界面
 *
 * @author SenseTech
 *
 */
public class HomeKnowledgeActivity extends BaseActivity implements
		View.OnClickListener {

	private RelativeLayout rl_tofile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_knowledge_activity);
		initViews();
		initListener();
	}

	private void initListener() {
		rl_tofile.setOnClickListener(this);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		initTopBar("知识库", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		rl_tofile = (RelativeLayout) findViewById(R.id.rl_tofile);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.rl_tofile:
				toOther();
				break;
			default:
				break;
		}
	}

	private void toOther() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(HomeKnowledgeActivity.this, RelevantFileActivity.class);
		startActivity(intent);
		// 设置切换动画，从左边进入，右边退出
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}
}
