package com.zchk.yunzichan.ui.activity.diector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;

/**
 * Created by admin on 2016/11/17.
 */
public class DiectorDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_userName;
    private TextView tv_userRole;
    private TextView tv_userNumber;

    private String userName;
    private String telNumber;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diector_detial_activity);
        initViews();
        initListeners();
    }

    private void initListeners() {
        tv_userNumber.setOnClickListener(this);
    }

    private void initViews() {

        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_userRole = (TextView) findViewById(R.id.tv_userRole);
        tv_userNumber = (TextView) findViewById(R.id.tv_userNumber);
        Intent intent=getIntent();
        userName=intent.getStringExtra("data");
        telNumber=intent.getStringExtra("number");
        role=intent.getStringExtra("role");

        tv_userName.setText("用户名："+userName);
        tv_userNumber.setText("电话："+telNumber);
        tv_userRole.setText("角色名："+role);
        initTopBar(userName,true,false,true);
        initTopBarListener(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_userNumber:
            {

                //调用系统的拨号服务实现电话拨打功能
                //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18649188902"));
                if (ActivityCompat.checkSelfPermission(DiectorDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);//内部类
            }
            break;
            default:
                break;
        }
    }
}
