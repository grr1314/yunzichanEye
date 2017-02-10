package com.zchk.yunzichan.ui.activity.query;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.UserInfo;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.fragment.leader.CheckQueryFragment;
import com.zchk.yunzichan.ui.fragment.leader.MaintenanceQueryFragment;
import com.zchk.yunzichan.utils.Constant;


/**
 * 领导督察
 *
 * @author SenseTech
 */
public class CheckAndMaintenanceQueryActivity extends BaseActivity implements
        OnClickListener {

    private String actTime;

    private TextView check_maintenance_btn1;
    private TextView check_maintenance_btn2;

    private LinearLayout ll_LeadCheck;
    private LinearLayout ll_LeadMaintance;

    private Fragment checkQueryFragment;
    private Fragment maintenanceQueryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_maintenance_query);

        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        check_maintenance_btn1.setOnClickListener(this);
        check_maintenance_btn2.setOnClickListener(this);
    }

    private void initView() {
        ll_LeadCheck = (LinearLayout) findViewById(R.id.ll_LeadCheck);
        ll_LeadMaintance = (LinearLayout) findViewById(R.id.ll_LeadMaintance);


        check_maintenance_btn1 = (TextView) findViewById(R.id.check_maintenance_btn1);
        check_maintenance_btn2 = (TextView) findViewById(R.id.check_maintenance_btn2);
        // 初始化topbar
        initTopBar("领导督查", true, false, true);
        // 初始化topbar的事件,必須在initTopBar调用
        initTopBarListener(null, null, new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // resetBtn();
        // 设置内容区域
        switch (i) {
            case 0:
                if (checkQueryFragment == null) {
                    checkQueryFragment = new CheckQueryFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", Constant.CheckAndMaintenanceQueryActivity);
                    checkQueryFragment.setArguments(args);
                    transaction.add(R.id.check_maintenance_fragment,
                            checkQueryFragment);
                } else {
                    transaction.show(checkQueryFragment);
                }
                // check_maintenance_btn1
                // .setBackgroundResource(R.drawable.btn_fra_sty_pressed);

                break;
            case 1:
                if (maintenanceQueryFragment == null) {
                    maintenanceQueryFragment = new MaintenanceQueryFragment();
                    transaction.add(R.id.check_maintenance_fragment,
                            maintenanceQueryFragment);
                } else {
                    transaction.show(maintenanceQueryFragment);
                }
                // check_maintenance_btn2
                // .setBackgroundResource(R.drawable.btn_fra_sty_pressed);

                break;
            default:
                break;
        }

        transaction.commit();
    }

    public void FragmentShowDialog() {
        showDialog();
    }

    public void FragmentDismissDialog() {
        dialogDismiss();
    }

    public UserInfo getUser() {
        return initApplication().userInfo;
    }

    private void resetBtn() {
        check_maintenance_btn1.setBackgroundResource(R.drawable.btn_fra_sty);
        check_maintenance_btn2.setBackgroundResource(R.drawable.btn_fra_sty);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (checkQueryFragment != null) {
            transaction.hide(checkQueryFragment);
        }
        if (maintenanceQueryFragment != null) {
            transaction.hide(maintenanceQueryFragment);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.check_maintenance_btn1:
                setSelect(0);
                ll_LeadCheck.setBackgroundResource(R.drawable.bg_leadercheck_pressed);
                ll_LeadMaintance.setBackgroundResource(R.drawable.bg_leadermain_numal);
                check_maintenance_btn1.setTextColor(Color.parseColor("#ffffff"));
                check_maintenance_btn2.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.check_maintenance_btn2:
                setSelect(1);
                ll_LeadMaintance.setBackgroundResource(R.drawable.bg_leadermain_pressed);
                ll_LeadCheck.setBackgroundResource(R.drawable.bg_leadercheck_numal);
                check_maintenance_btn1.setTextColor(Color.parseColor("#000000"));
                check_maintenance_btn2.setTextColor(Color.parseColor("#ffffff"));
                break;
            default:
                break;
        }
    }

}
