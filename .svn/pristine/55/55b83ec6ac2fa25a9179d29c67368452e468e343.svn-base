package com.zchk.yunzichan.ui.activity.leader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.errorRecord;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.fragment.leader.DealContentFragment;

import java.io.Serializable;

/**
 * Created by admin on 2016/12/5.
 */
public class DealActivity extends BaseActivity {
    private errorRecord errorRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_charts_activity);
        Intent intent = getIntent();
        errorRecord = (com.zchk.yunzichan.entity.bean.errorRecord) intent.getSerializableExtra("bean");
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putSerializable("bean", (Serializable) errorRecord);
            DealContentFragment fragment = new DealContentFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.leader_chect_content, fragment).commit();
        }
        initViews();
    }

    private void initViews() {
        initTopBar("处理流程", true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
}
