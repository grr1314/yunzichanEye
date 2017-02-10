package com.zchk.yunzichan.ui.activity.check;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.check.CheckDetailCmd;
import com.zchk.yunzichan.entity.model.check.CheckInfoDetail;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.view.NoScrollListView;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by admin on 2016/11/18.
 */
public class CheckDeteilActivity extends BaseActivity {

    private static final String TAG = "CheckDeteilActivity";
    private String url = GlobalDefine.CmdPath.cmdPath + "CheckItemInfoByTemplateSearchCmd";

    private CheckDetailCmd checkDetailCmd;//请求结构
    private CheckInfoDetail checkInfoDetail;//返回的数据结构
    private String tempCode;
    private String deviceId;

    private TextView tv_deviceId;
    private TextView tv_deviceName;
    private TextView tv_deviceType;
    private TextView tv_deviceS;
    private TextView tv_deviceTime;
    private TextView tv_peopleCheck;
    private TextView tv_checkContent;

    private NoScrollListView ls_allTemp;

    MyAdapter adapter;

    List<Map<String, String>> list = new ArrayList<>();
    Map<String, String> item;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_detail_activity);

        Intent intent = getIntent();
        tempCode = intent.getStringExtra("tempCode");
        deviceId = intent.getStringExtra("deviceId");
        time = intent.getStringExtra("time");
        initViews();
        iniData();
    }

    private void iniData() {
        RequestAndResponse res = new RequestAndResponse();
        res.requestCommand = deviceId + "%" + time;
        res.responseCommand = tempCode;
        checkDetailCmd = new CheckDetailCmd();
        checkDetailCmd.userAccountName = MyApplication.userInfo.getAccount();
        checkDetailCmd.resp = res;

        // 将数据打包成json字符串
        String param = JsonTools.StringToJson_CheckDetail(checkDetailCmd);
        http(url, 1, param, new StringCallback() {
            @Override
            public void onAfter(int code) {
                super.onAfter(code);
                dialogDismiss();
            }

            @Override
            public void onBefore(Request request, int code) {
                super.onBefore(request, code);
                showDialog();
            }

            @Override
            public void onResponse(String response, int code) {
                Log.e(TAG, "第一:" + response);
                dealData(response);
            }

            @Override
            public void onError(Call call, Exception e, int code) {
                showErrorPage();
            }
        });
    }

    private void dealData(String response) {
        checkInfoDetail = new CheckInfoDetail();
        checkInfoDetail = (CheckInfoDetail) JsonTools.JsonToStruts(
                response, CheckInfoDetail.class);
        if (checkInfoDetail.item==null)
        {
            return;
        }
        tv_deviceId.setText("设备编号：" + checkInfoDetail.item[0].qrCode);
        tv_deviceName.setText("设备名称：" + checkInfoDetail.item[0].nameCn);
        tv_deviceType.setText("设备类型："+checkInfoDetail.item[0].typeCn);
        tv_deviceS.setText("供应商：" + checkInfoDetail.item[0].companyCn);
        tv_deviceTime.setText("点检时间：" + checkInfoDetail.item[0].checkTime);
        tv_peopleCheck.setText("点检人：" + checkInfoDetail.item[0].checkUser);
        for (int i = 0; i < checkInfoDetail.item[0].checkNote.length; i++) {
            item = new HashMap<>();
            item.put("nameCn", checkInfoDetail.item[0].checkNote[i].nameCn);
            item.put("value", checkInfoDetail.item[0].checkNote[i].value);
            list.add(item);
        }
        ls_allTemp.setAdapter(new MyAdapter());
    }

    private void initViews() {
        tv_deviceId = (TextView) findViewById(R.id.tv_deviceId);
        tv_deviceName = (TextView) findViewById(R.id.tv_deviceName);
        tv_deviceType= (TextView) findViewById(R.id.tv_deviceType);
        tv_deviceS = (TextView) findViewById(R.id.tv_deviceS);
        tv_deviceTime = (TextView) findViewById(R.id.tv_deviceTime);
        tv_peopleCheck = (TextView) findViewById(R.id.tv_peopleCheck);

        ls_allTemp = (NoScrollListView) findViewById(R.id.ls_allTemp);

        adapter = new MyAdapter();
        initTopBar("点检详情", true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CheckDeteilActivity.this).inflate(R.layout.item_check_detail, null);
                mViewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item_detail);
                mViewHolder.tv_item_Type= (TextView) convertView.findViewById(R.id.tv_item_Type);
                convertView.setTag(mViewHolder);
            } else
                mViewHolder = (ViewHolder) convertView.getTag();
            mViewHolder.tv_item.setText(list.get(position).get("value"));
            mViewHolder.tv_item_Type.setText(list.get(position).get("nameCn"));
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv_item;
        TextView tv_item_Type;

    }
}
