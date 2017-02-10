package com.zchk.yunzichan.ui.activity.check;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.model.check.CheckDeviceInfosByDeviceIdMessage;
import com.zchk.yunzichan.http.HttpRequest;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;


/**
 * 通过id来搜索检点信息
 *
 * @author SenseTech
 */
public class CheckByIdActivity extends BaseActivity {
    //IP地址
    private String url = GlobalDefine.CmdPath.cmdPath + "CheckInfosByDeviceIdAddCmd";

    private CheckDeviceInfosByDeviceIdMessage persons;

    private Thread checkIdThread;
    private Runnable networkTask;
    private Handler checkIdHandler;
    private String respJson;
    public EditText deviceid;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.check_byid);
        initView();
        initEven();
        handlerByID();
    }

    public void initView() {
        deviceid = (EditText) findViewById(R.id.check_text);
        button = (Button) findViewById(R.id.check_button_luru);

        initTopBar("设备巡检", true, false, true);
        initTopBarListener(null, null, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initEven() {
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (checkMode() == Constant.NETWORKOFF) {
                    ToastUtil.showToast(getApplicationContext(), "请检查网络连接");
                    return;
                }
                initThread();
            }
        });
    }

    public void initThread() {
        checkIdThread = new Thread(networkTask);
        checkIdThread.start();
    }

    public void IntentFinish() {
        String deviceID = deviceid.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("ID", deviceID);
        intent.setClass(CheckByIdActivity.this, CheckInsertActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        this.finish();
    }

    public void handlerByID() {
        checkIdHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("json");
                respJson = val;
                Log.e("mylog", "CheckId的请求结果为-->" + val);

                // UI界面的更新等相关操作
                if (!val.equals("")) {
                    persons = (CheckDeviceInfosByDeviceIdMessage) JsonTools.JsonToStruts(respJson, CheckDeviceInfosByDeviceIdMessage.class);
                    if (persons.responseCommand.equals("OK")) {
                        IntentFinish();
                    } else if (persons.responseCommand.equals("noDeviceId")) {
                        ToastUtil.showToast(getApplicationContext(), "系统中无此设备ID");
                    } else {
                        ToastUtil.showToast(getApplicationContext(), "服务器出现故障，请重新提交");
                    }

                } else {
                    ToastUtil.showToast(getApplicationContext(), "抱歉！当前处于断线状态");
                    IntentFinish();
                }
            }
        };

        networkTask = new Runnable() {

            @Override
            public void run() {
                // 在这里进行 http request.网络请求相关操作
                CheckDeviceInfosByDeviceIdMessage request = new CheckDeviceInfosByDeviceIdMessage();
                request.deviceId = deviceid.getText().toString().trim();
                request.userName = initApplication().userInfo.getAccount();
                String param = JsonTool(request);

                //做网络请求
                String resp = HttpRequest.sendPost(url.toString(), param);

                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("json", resp);
                msg.setData(data);
                checkIdHandler.sendMessage(msg);
            }
        };
    }

    // ARRAY to JSON
    private String JsonTool(CheckDeviceInfosByDeviceIdMessage request) {
        String jsonString = JSON.toJSONString(request);

        int lastIndexOf = jsonString.indexOf("null");
        while (lastIndexOf != -1) {

            if (lastIndexOf != -1) {
                jsonString = jsonString.substring(0, lastIndexOf) + jsonString.substring(lastIndexOf + 4, jsonString.length());
            }
            if (lastIndexOf == -1) {
                break;
            }
            lastIndexOf = jsonString.indexOf("null");
        }
        System.out.println("集合对象生成:" + jsonString);
        return jsonString;
    }
}
