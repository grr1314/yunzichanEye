package com.zchk.yunzichan.ui.activity.leader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.query.CheckAndMaintenanceQueryActivity;
import com.zchk.yunzichan.ui.fragment.leader.LeaderCheckContent;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by admin on 2016/11/25.
 */
public class LeaderCheckChartsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_charts_activity);
        Log.e("user","user:"+ MyApplication.userInfo.getAccount());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.leader_chect_content, new LeaderCheckContent()).commit();
        }
        initViews();
    }

    private void initViews() {
        initTopBar("领导督查", true, true, true);
        initTopBarListener(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LeaderCheckChartsActivity.this, CheckAndMaintenanceQueryActivity.class);
                startActivity(intent);
            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取http方法
     */
    public String getHttp(String str, int type, String param,
                          Callback<String> call) {
        return http(str, type, param, call);
    }
}
