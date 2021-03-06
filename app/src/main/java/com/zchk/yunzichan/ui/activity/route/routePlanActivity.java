package com.zchk.yunzichan.ui.activity.route;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.checkroute.DeviceListInfoMessage;
import com.zchk.yunzichan.entity.model.checkroute.DeviceListInfoQueryMessage;
import com.zchk.yunzichan.entity.model.checkroute.TaskRouteActionQueryMessage;
import com.zchk.yunzichan.entity.model.checkroute.TaskRouteSuspendActionQueryMessage;
import com.zchk.yunzichan.minterface.DialogCallBack;
import com.zchk.yunzichan.ui.activity.ReasonActivity;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.adapter.CheckRoundAdapter;
import com.zchk.yunzichan.utils.DateUtils;
import com.zchk.yunzichan.utils.DialogUtil;
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
 * Created by admin on 2016/11/1.
 */
public class routePlanActivity extends BaseActivity implements View.OnClickListener, CheckRoundAdapter.adapterClick {

    private static final String urlSearch = GlobalDefine.CmdPath.cmdPath
            + "APPDeviceListInfoSearchCmd ";

    private static final String urlStart = GlobalDefine.CmdPath.cmdPath
            + "APPTaskRouteStartActionCmd";

    private static final String urlStop = GlobalDefine.CmdPath.cmdPath
            + "APPTaskRouteSuspendActionCmd";

    private static final String urlPause = GlobalDefine.CmdPath.cmdPath
            + "APPTaskRouteSuspendActionCmd";

    private ListView lv_check_round;

    private CheckRoundAdapter adapter;

    private List<Map<String, Object>> list = new ArrayList<>();
    private Map<String, Object> item;


    private DeviceListInfoQueryMessage request;
    private DeviceListInfoMessage routeList;


    private TaskRouteActionQueryMessage requestStart;//开始路线的请求结构

    private TaskRouteSuspendActionQueryMessage requestEnd;//结束或暂停路线请求

    private int routeId;
    private String TaskId;
    private String routeName;
    private int stopType;

    private TextView tv_routeName;
    private Button btn_start;
    private Button btn_pause;
    private Button btn_end;


    private boolean isStart = false;

    private RequestAndResponse responseCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_plan_activity);
        Intent intent = getIntent();
        routeName = intent.getStringExtra("routeName");
        routeId = intent.getIntExtra("routeId", 0);
        initViews();
    }

    private void initViews() {
        initTopBar("巡检自查", true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_end = (Button) findViewById(R.id.btn_end);
        lv_check_round = (ListView) findViewById(R.id.lv_check_round);
        tv_routeName = (TextView) findViewById(R.id.tv_routeName);

        adapter = new CheckRoundAdapter(routePlanActivity.this, list, routeId);
        adapter.setAdapterClick(this);
        lv_check_round.setAdapter(adapter);

        initData();
        initListeners();
    }

    private void initListeners() {
        btn_pause.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_end.setOnClickListener(this);
    }

    private void initData() {
        String param = buildRequest();
        http(urlSearch, 1, param, new StringCallback() {

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

    public void dealData(String response) {
        if (response.equals("")) {
            //ShowEmptyView
            return;
        } else {
            routeList = parseString(response);
            if (routeList.responseCommand.equals("")) {
                //showemptyView
                return;
            } else if (routeList.item == null) {
                //showemptyView
                return;
            } else {
                putData();
            }
        }
    }

    protected void putData() {
        tv_routeName.setText("路线名称：" + routeName);
        int length = routeList.item.length;
        for (int i = 0; i < length; i++) {
            item = new HashMap<>();
            item.put("deviceName", routeList.item[i].DeviceNameCn);
            item.put("labelQr", routeList.item[i].QrCode);
            list.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    private DeviceListInfoMessage parseString(String json) {
        return (DeviceListInfoMessage) JsonTools.JsonToStruts(json,
                DeviceListInfoMessage.class);
    }

    private RequestAndResponse parseStartString(String json) {
        return (RequestAndResponse) JsonTools.JsonToStruts(json,
                RequestAndResponse.class);
    }

    private String buildRequest() {
        request = new DeviceListInfoQueryMessage();
        request.routeId = routeId;
        request.userAccountName = MyApplication.userInfo.getAccount();
        return JsonTools.StringToJson_CheckSingleRoute(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pause: {
                Intent intent = new Intent();
                intent.setClass(routePlanActivity.this, ReasonActivity.class);
                intent.putExtra("url", urlPause);
                intent.putExtra("routeId", routeId);
                intent.putExtra("stopType", 0);
                startActivity(intent);
            }
            break;
            case R.id.btn_start: {
                startCheck();
            }
            break;
            case R.id.btn_end: {
                endCheck();
            }
            break;
            default:
                break;
        }
    }

    private void endCheck() {
        DialogUtil.showMyDialog(routePlanActivity.this, "是否结束路线", new DialogCallBack() {
            @Override
            public void sure() {
                Intent intent = new Intent();
                intent.setClass(routePlanActivity.this, ReasonActivity.class);
                intent.putExtra("url", urlStop);
                intent.putExtra("routeId", routeId);
                intent.putExtra("stopType", 1);
                startActivity(intent);
                DialogUtil.dismissMyDialog();

            }

            @Override
            public void cancel() {
                DialogUtil.dismissMyDialog();
            }
        });
    }

    private void dealEndData(String response) {
        responseCmd = parseStartString(response);
        if (responseCmd.responseCommand.equals("OK")) {
            isStart = true;
            btn_start.setText("已开始");
            btn_start.setEnabled(false);
            btn_pause.setEnabled(false);
            btn_end.setEnabled(false);
        }
    }

    private void startCheck() {

        DialogUtil.showMyDialog(routePlanActivity.this, "是否开始路线", new DialogCallBack() {
            @Override
            public void sure() {
                //进行网络操作
                String param = bulidRequestStart();
                http(urlStart, 1, param, new StringCallback() {

                    @Override
                    public void onAfter(int code) {
                        super.onAfter(code);
                        dialogDismiss();
                        DialogUtil.dismissMyDialog();
                    }

                    @Override
                    public void onBefore(Request request, int code) {
                        super.onBefore(request, code);
                        showDialog();
                    }

                    @Override
                    public void onResponse(String response, int code) {
                        Log.e("Home", response);
                        dealStartData(response);
                    }

                    @Override
                    public void onError(Call call, Exception e, int code) {
                        showErrorPage();
                    }
                });
                //在网络请求成功以后将isStart设置为true
                isStart = true;
            }


            @Override
            public void cancel() {
                DialogUtil.dismissMyDialog();
            }
        });

    }

    private void dealStartData(String response) {
        responseCmd = parseStartString(response);
        if (responseCmd.responseCommand.equals("OK")) {
            isStart = true;
            btn_start.setText("已开始");
            btn_start.setEnabled(false);
            TaskId = responseCmd.requestCommand;
        }

    }

    private String bulidRequestStart() {
        requestStart = new TaskRouteActionQueryMessage();
        requestStart.routeId = routeId;
        requestStart.startTime = DateUtils.getTime();
        requestStart.useraccountName = MyApplication.userInfo.getAccount();
        return JsonTools.StringToJson_CheckStart(requestStart);
    }

    @Override
    public void adapterClick(int pos) {
        adapter.openZxing(list.get(pos).get("labelQr").toString(), TaskId);
    }
}
