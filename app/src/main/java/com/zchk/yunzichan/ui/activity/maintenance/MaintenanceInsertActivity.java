package com.zchk.yunzichan.ui.activity.maintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import com.alibaba.fastjson.JSON;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.db.DatabaseHelper;
import com.zchk.yunzichan.entity.model.maintenance.MaintainAddMaintainInfosMessage;
import com.zchk.yunzichan.entity.model.maintenance.MaintainlastMaintainInfosQueryMessage;
import com.zchk.yunzichan.entity.model.maintenance.lastMaintainInfosQuery;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.main.HomeMaintenanceIndexActivity;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.DateUtils;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 维保信息详情界面，本界面负责显示并修改提交数据
 *
 * @author SenseTech
 */
public class MaintenanceInsertActivity extends BaseActivity {

    private static final String TAG = "MaintenanceInsertActivity";
    private static String urllastOne = GlobalDefine.CmdPath.cmdPath
            + "MaintainInfosLastOneSearchCmd";
    private static String urlInsert = GlobalDefine.CmdPath.cmdPath
            + "MaintainInfosAddCmd";

    public MaintainlastMaintainInfosQueryMessage persons;
    public MaintainAddMaintainInfosMessage localData;

    public Thread lastOneThread;
    public Thread insertThread;
    public Thread cameraThread;

    public Runnable networkTask;

    public Handler handlerInsert; // 添加信息
    public Runnable networkTaskInsert;

    public String respJson;

    public String deviceId;

    public TextView maintenance_devicename;
    public TextView maintenance_devicesnamecn;
    public TextView maintenance_devicechangshang;
    public TextView maintenance_devicetype;
    public TextView maintenance_devicesno;
    public TextView maintenance_time;

    public EditText add_content;

    public Button maintenance_btn_info;

    public DBUtils dbu;
    public static DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_insert);
        initView();

        Intent intent = getIntent();
        deviceId = intent.getStringExtra("ID");

        initLinstener();
        if (checkMode() == Constant.NETWORKOFF) {

            initDataLoc();
            return;
        }
        initData();
    }

    private void initDataLoc() {
        dbu = new DBUtils(initApplication());
        showDialog();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    // 查询本地数据
                    List<Map<String, Object>> ls = dbu
                            .selectDeviceById(deviceId);
                    Message message = Message.obtain(handler, 1, ls);
                    message.sendToTarget();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    dialogDismiss();
                    List<Map<String, Object>> ls = (List<Map<String, Object>>) msg.obj;
                    maintenance_devicename.setText(ls.get(0).get("nameCn")
                            .toString());
//                    maintenance_devicesnamecn.setText(ls.get(0).get("nameCn")
//                            .toString());
                    maintenance_devicechangshang.setText(ls.get(0).get("company")
                            .toString());
                    maintenance_devicetype.setText(ls.get(0).get("typeName")
                            .toString());
                    maintenance_devicesno.setText(ls.get(0).get("lableCode")
                            .toString());
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void initData() {
        // TODO Auto-generated method stub
        MaintainlastMaintainInfosQueryMessage userCheckrequ = new MaintainlastMaintainInfosQueryMessage();
        userCheckrequ.maintainlastInfo = new lastMaintainInfosQuery[1];
        userCheckrequ.deviceId = deviceId;
        userCheckrequ.userName = initApplication().userInfo.getAccount();
        String param = JsonTool(userCheckrequ);

        http(urllastOne, 1, param, new StringCallback() {
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
                persons = (MaintainlastMaintainInfosQueryMessage) JsonTools
                        .JsonToStruts(response,
                                MaintainlastMaintainInfosQueryMessage.class);
                putData();
            }

            @Override
            public void onError(Call call, Exception e, int code) {
                showErrorPage();
            }
        });

    }

    public void initView() {
        maintenance_devicename = (TextView) findViewById(R.id.check_devicename); // 当前设备
        maintenance_devicesno = (TextView) findViewById(R.id.check_devicesno);// 设备名称
        maintenance_devicechangshang = (TextView) findViewById(R.id.check_devicechangshang);// 设备厂商
        maintenance_devicetype = (TextView) findViewById(R.id.check_devicetype);// 设备类型
        maintenance_time = (TextView) findViewById(R.id.maintenance_time);// 上次点检时间和状态

        add_content = (EditText) findViewById(R.id.add_content);// 结果

        maintenance_btn_info = (Button) findViewById(R.id.maintenance_btn_info);

        // 初始化topbar
        initTopBar("设备维修", true, false, true);
        // 初始化topbar的事件,必須在initTopBar调用
        initTopBarListener(null, null, new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    public void initLinstener() {
        // 点检信息提交
        maintenance_btn_info.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (Constant.NETWORKOFF == checkMode()) {
                    insertToLoc();
                    return;
                }
                initInsert();
            }
        });
    }

    protected void insertToLoc() {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("checkUser", MyApplication.userInfo.getAccount());
        item.put("assetCode", maintenance_devicesno.getText());
        item.put("checkTime", DateUtils.getTime());
        item.put("status", "1");
        item.put("checkValue", "1");
        item.put("note", add_content.getText());
        list.add(item);
        try {
            dbu.insertMaintain(list);
        } catch (Exception e) {
            // TODO: handle exception
        }
        toSearch();
        ToastUtil.showToast(getApplicationContext(), "添加成功");
    }

    public void initInsert() {
        MaintainAddMaintainInfosMessage userCheckAdd = new MaintainAddMaintainInfosMessage();
        userCheckAdd.deviceId = deviceId;
        userCheckAdd.userName =MyApplication.userInfo.getAccount();
        userCheckAdd.repairInfos = add_content.getText().toString();
        localData = userCheckAdd;

        String param = JsonToolInsert(userCheckAdd);

        http(urlInsert, 1, param, new StringCallback() {
            @Override
            public void onBefore(Request request, int code) {
                super.onBefore(request, code);
                showDialog();
            }

            @Override
            public void onAfter(int code) {
                super.onAfter(code);
                dialogDismiss();
            }

            @Override
            public void onResponse(String response, int code) {
                Log.e(TAG,response);
                persons = (MaintainlastMaintainInfosQueryMessage) JsonTools
                        .JsonToStruts(response,
                                MaintainlastMaintainInfosQueryMessage.class);
                if (persons.responseCommand.equals("OK")) {
                    toSearch();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "添加失败");
                }
            }

            @Override
            public void onError(Call call, Exception e, int code) {
                // TODO Auto-generated method stub
            }
        });
    }

    protected void toSearch() {
        Intent intent = new Intent();
        intent.setClass(MaintenanceInsertActivity.this,
                MaintenanceQueryActivity.class);
        startActivity(intent);
        finish();
    }

    public void isInsert() {
        if (persons.responseCommand.equals("OK")) {
            Toast.makeText(MaintenanceInsertActivity.this, "添加维保数据至服务器成功",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MaintenanceInsertActivity.this, "添加维保数据至服务器失败",
                    Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent();
        intent.setClass(MaintenanceInsertActivity.this,
                HomeMaintenanceIndexActivity.class);
        startActivity(intent);
        // 设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        MaintenanceInsertActivity.this.finish();
    }

    private static String JsonTool(
            MaintainlastMaintainInfosQueryMessage userCheckrequ) {

        String jsonString = JSON.toJSONString(userCheckrequ);

        int lastIndexOf = jsonString.indexOf("null");
        while (lastIndexOf != -1) {

            if (lastIndexOf != -1) {
                jsonString = jsonString.substring(0, lastIndexOf)
                        + jsonString.substring(lastIndexOf + 4,
                        jsonString.length());
            }
            if (lastIndexOf == -1) {
                break;
            }
            lastIndexOf = jsonString.indexOf("null");
        }
        System.out.println("集合对象生成:" + jsonString);

        return jsonString;
    }

    private static String JsonToolInsert(
            MaintainAddMaintainInfosMessage userCheckAdd) {

        String jsonString = JSON.toJSONString(userCheckAdd);

        int lastIndexOf = jsonString.indexOf("null");
        if (lastIndexOf != -1) {
            jsonString = jsonString.substring(0, lastIndexOf)
                    + jsonString
                    .substring(lastIndexOf + 4, jsonString.length());
        }
        System.out.println("集合对象生成:" + jsonString);

        return jsonString;
    }

    private void putData() {
        if (persons.maintainlastInfo == null) {
            maintenance_devicename.setText("暂无");
            maintenance_devicechangshang.setText("暂无");
            maintenance_devicetype.setText("暂无");
            maintenance_devicesno.setText("暂无");
            ToastUtil.showToast(getApplicationContext(), "暂无数据");
            return;
        }
        String name = persons.maintainlastInfo[0].deviceName;
        String factory = persons.maintainlastInfo[0].deviceManufacturer;
        String type = persons.maintainlastInfo[0].deviceType;
        String number = persons.maintainlastInfo[0].deviceNum;
        maintenance_devicename.setText(name);
        maintenance_devicechangshang.setText(factory);
        maintenance_devicetype.setText(type);
        maintenance_devicesno.setText(number);

        String time_state = "";
        time_state = "上次点检时间:" + persons.maintainlastInfo[0].checkTime;
        maintenance_time.setText(time_state);
    }

}
