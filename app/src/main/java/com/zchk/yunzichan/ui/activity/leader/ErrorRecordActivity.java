package com.zchk.yunzichan.ui.activity.leader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.fragment.leader.ErrorContentFragment;

/**
 * Created by admin on 2016/12/5.
 */
public class ErrorRecordActivity extends BaseActivity {

    private static final String TAG = "ErrorRecordActivity";
    private String id;
    private String strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_charts_activity);
        Intent intent = getIntent();
        strs = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        Log.e(TAG, "id:" + id);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putString("name", strs);
            args.putString("id", id);
            ErrorContentFragment fragment = new ErrorContentFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.leader_chect_content, fragment).commit();
        }
        initViews();
    }

    private void initViews() {
        initTopBar("异常记录", true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}