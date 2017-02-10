package com.zchk.yunzichan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.checkroute.CheckRouteSearchMessage;
import com.zchk.yunzichan.entity.model.checkroute.TaskRouteSuspendActionQueryMessage;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.utils.DateUtils;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by admin on 2016/11/2.
 * 超时原因以及暂停停止原因
 */
public class ReasonActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ReasonActivity";
    Button btn_resson_Add;
    EditText ed_reason;

    String reason;
    private String title;
    private int routeId;
    private String url;
    private int stopType;
    MyApplication application;

    RequestAndResponse res;
    private TaskRouteSuspendActionQueryMessage task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reason_activity);
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        routeId = intent.getIntExtra("routeId", 0);
        url = intent.getStringExtra("url");
        stopType = intent.getIntExtra("stopType", -1);
        title = "原因";
        initTopBar(title, true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_resson_Add = (Button) findViewById(R.id.btn_resson_Add);
        ed_reason = (EditText) findViewById(R.id.ed_reason);
        btn_resson_Add.setOnClickListener(this);
    }

    private void postMethod() {
        String param = buildRequest();
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
                Log.e("Home", response);
                dealData(response);
            }

            @Override
            public void onError(Call call, Exception e, int code) {
                showErrorPage();
            }
        });
    }

    /**
     * 返回的数据处理
     *
     * @param response
     */
    private void dealData(String response) {
        this.res = parseString(response);
        if (res.responseCommand.equals("OK")) ;
        {
            if (stopType==1)
            {
                ToastUtil.showToast(ReasonActivity.this, "路线已结束");
            }
            else
                ToastUtil.showToast(ReasonActivity.this, "路线已暂停");

            setResult(stopType);
            finish();
        }

    }

    private String buildRequest() {
        task = new TaskRouteSuspendActionQueryMessage();
        task.stopType = stopType;
        task.routeId = routeId;
        task.startTime = DateUtils.getTime();
        task.endTime = DateUtils.getTime();
        task.season = reason;
        task.userAccountName = MyApplication.userInfo.getAccount().toString();
        return JsonTools.StringToJson_CheckRoutePause(task);
    }

    private RequestAndResponse parseString(String json) {
        return (RequestAndResponse) JsonTools.JsonToStruts(json,
                RequestAndResponse.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resson_Add: {
                postMethod();
            }
            break;
        }
    }
}
