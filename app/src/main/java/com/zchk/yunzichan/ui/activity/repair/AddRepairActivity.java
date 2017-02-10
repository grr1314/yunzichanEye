package com.zchk.yunzichan.ui.activity.repair;

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
import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.check.CheckByDeviceBasicInfoItem;
import com.zchk.yunzichan.entity.model.check.CheckByDeviceBasicInfoMessage;
import com.zchk.yunzichan.entity.model.check.CheckTemplateMessage;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;
import com.zchk.yunzichan.http.HttpRequest;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.DateUtils;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.StringUtils;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * 添加报修信息
 *
 * @author SenseTech
 *
 */
@SuppressLint({ "HandlerLeak", "NewApi" })
public class AddRepairActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "AddRepairActivity";

    private static final long waitTime = 5000;// 默认的等待时间

    private static String url = GlobalDefine.CmdPath.cmdPath
            + "GetCheckTemplateCmd";

    // 获取最新一条数据的url
    private static String urllastOne = GlobalDefine.CmdPath.cmdPath
            + "CheckByDeviceBasicInfoCmd";

    private CheckTemplateMessage checkTemplateMessage;
    // 报修单添加接口
    private static final String ADDRESS_ADD = GlobalDefine.CmdPath.cmdPath
            + "MaintenanceOrderAddDraftCmd";

    Button btn_upLoad;
    RequestAndResponse AddRepairFeedback;

    CheckByDeviceBasicInfoMessage persons;

    private EditText ed_orderCode;// 编号
    private EditText ed_phone;// 报修电话
    private EditText ed_reportPerson;// 报修人
    private EditText ed_receptionUser;// 接待人
    private EditText ed_deviceNameCn;// 设备名称
    private EditText ed_deviceFather;// 所属建筑
    private EditText ed_faultReport;// 故障报告
    private EditText ed_repairUser;// 指派维修人
    private EditText ed_position;// 安装位置

    private String deviceId;

    private DBUtils dbu;

    public Thread lastOneThread;
    private Runnable networkTask;
    private String templateCode;// 点检设备的类型

    MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_repair_activity);
        initViews();
        application = initApplication();
        dbu = new DBUtils(application);
        initListeners();
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("ID");
        if (checkMode() == Constant.NETWORKOFF) {
            initDataLoc();
            return;
        }
        // 根据id查询对应的设备信息
        initData();

    }

    private void initDataLoc() {
        // TODO Auto-generated method stub
        showDialog();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(3000);
                    // 查询本地数据
                    List<Map<String, Object>> ls = dbu
                            .selectDeviceById(deviceId);
                    Message message = Message.obtain(handlerData, 2, ls);
                    message.sendToTarget();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 注意：这段代码与CheckInsertActivity中的代码一样
     */
    private void initData() {
        // TODO Auto-generated method stub
        CheckTemplateMessage ctm = new CheckTemplateMessage();
        ctm.deviceId = deviceId;
        String param = JsonToolCheck(ctm);
        http(url, 1, param, new StringCallback() {

            @Override
            public void onBefore(Request request,int code) {
                super.onBefore(request,code);
                showDialog();
            }

            @Override
            public void onAfter(int code) {
                super.onAfter(code);
                dialogDismiss();
            }

            @Override
            public void onResponse(String response,int code) {
                dealData(response);
            }

            @Override
            public void onError(Call call, Exception e,int code) {
                showErrorPage();
            }
        });
    }

    protected void dealData(String response) {

        if (response.isEmpty() || response.equals("")) {
            showErrorPage();
            return;
        }
        checkTemplateMessage = (CheckTemplateMessage) JsonTools.JsonToStruts(
                response, CheckTemplateMessage.class);
        if (checkTemplateMessage.responseCommand.equals("Fail")) {
            showErrorPage();
            return;
        } else {
            templateCode = checkTemplateMessage.items[0].templateCode;
            // 查询该设备的最后一条点检信息
            initLastOne();
            putData();
        }
    }

    private void putData() {
        ed_deviceNameCn.setText(checkTemplateMessage.deviceName);
        ed_orderCode.setText(checkTemplateMessage.deviceId);
        ed_reportPerson.setText(MyApplication.userInfo.getAccount());
    }

    protected void initLastOne() {

        lastOneThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                CheckByDeviceBasicInfoMessage userCheckrequ = new CheckByDeviceBasicInfoMessage();

                userCheckrequ.userName = MyApplication.userInfo
                        .getAccount();
                userCheckrequ.deviceId = deviceId;

                // 将数据打包成json字符串
                String param = JsonTools.StringToJson_lastCheck(userCheckrequ);
                System.out.println("param JsonStr:" + param);
                String resp = HttpRequest.sendPost(urllastOne.toString(), param);

                Log.e("INFO", "return lastData:" + resp);
                persons = (CheckByDeviceBasicInfoMessage) JsonTools
                        .JsonToStruts(resp, CheckByDeviceBasicInfoMessage.class);
                if (persons.responseCommand.equals("OK")) {
                    CheckByDeviceBasicInfoItem infos = persons.item[0];
                    Message msg = Message.obtain(handlerData, 1, infos);
                    msg.sendToTarget();
                }
            }
        });
        lastOneThread.start();
    }

    Handler handlerData = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    CheckByDeviceBasicInfoItem infos = (CheckByDeviceBasicInfoItem) msg.obj;
                    ed_deviceNameCn.setText(infos.deviceName);
                    ed_orderCode.setText(infos.deviceNum);
                    ed_reportPerson.setText(MyApplication.userInfo.getAccount());
                    break;
                case 2:
                    List<Map<String, Object>> ls = (List<Map<String, Object>>) msg.obj;
                    if (ls == null) {
                        ToastUtil.showToast(getApplicationContext(), "暂无设备");
                        return;
                    }
                    ed_deviceNameCn.setText(ls.get(0).get("nameCn").toString());
                    ed_orderCode.setText(ls.get(0).get("lableCode").toString());
                    ed_reportPerson.setText(MyApplication.userInfo.getAccount());
                    dialogDismiss();
                    break;
                default:
                    break;
            }
        };
    };

    private static String JsonToolCheck(CheckTemplateMessage checkTemp) {

        String jsonString = JSON.toJSONString(checkTemp);

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
        Log.e(TAG, "集合对象生成:" + jsonString);
        return jsonString;
    }

    private void initListeners() {
        // TODO Auto-generated method stub
        btn_upLoad.setOnClickListener(this);
    }

    private void initViews() {
        // TODO Auto-generated method stub
        btn_upLoad = (Button) findViewById(R.id.btn_upLoad);
        ed_reportPerson = (EditText) findViewById(R.id.ed_reportPerson);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_orderCode = (EditText) findViewById(R.id.ed_orderCode);
        // ed_receptionUser = (EditText) findViewById(R.id.ed_receptionUser);
        ed_deviceNameCn = (EditText) findViewById(R.id.ed_deviceNameCn);
        ed_deviceFather = (EditText) findViewById(R.id.ed_deviceFather);
        ed_repairUser = (EditText) findViewById(R.id.ed_repairUser);
        ed_faultReport = (EditText) findViewById(R.id.ed_faultReport);
        ed_position = (EditText) findViewById(R.id.ed_position);

        ed_phone.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });

        // 初始化topbar
        initTopBar("提交报修单", true, false, true);
        // 初始化topbar的事件,必須在initTopBar调用
        initTopBarListener(null, null, new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message arg0) {
            // TODO Auto-generated method stub
            dialogDismiss();
            switch (arg0.what) {
                case 1: {
                    String res = (String) arg0.obj;
                    if (!isShow(res)) {
                        ToastUtil.showToast(getApplicationContext(), "数据添加失败！");
                        // 设置空数据图片
                        // lv_repair.setEmptyView(tv_null);
                    } else {
                        AddRepairFeedback = (RequestAndResponse) JsonTools
                                .JsonToStruts(res, RequestAndResponse.class);
                        Log.e("TAG", AddRepairFeedback.responseCommand);
                        if (AddRepairFeedback.responseCommand.equals("OK")) {
                            ToastUtil.showToast(getApplicationContext(), "数据添加成功！");
                        }
                        toSearch();
                    }

                }
                break;

                default:
                    break;
            }
        }
    };

    /**
     * 判断返回的数据是否为空
     *
     * @param res
     * @return
     */
    @SuppressLint("NewApi")
    protected boolean isShow(String res) {
        // TODO Auto-generated method stub
        if (res.equals("") && res.isEmpty()) {
            // false
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.btn_upLoad: {
                // 提交保修单数据
                Addrepair();

            }
            break;
            default:
                break;
        }
    }

    private void JsonAddRepairFeedback(String jsonString) {
        AddRepairFeedback = JSON.parseObject(jsonString,
                RequestAndResponse.class);
    }

    /**
     * 判断是否填写数据完全
     *
     * @param reportPerson
     * @param phone
     * @param receptionUser
     * @param deviceNameCn
     * @param deviceFather
     * @param repairUser
     * @param faultReport
     * @return
     */
    @SuppressLint("NewApi")
    private boolean isDoneInput(String reportPerson, String phone,
                                String deviceNameCn, String deviceFather, String repairUser,
                                String faultReport, String position) {

        boolean isNull = true;
        String notNull = "该项不能为空";
        List<String> item = new ArrayList<String>();
        item.add(reportPerson);
        item.add(phone);
        item.add(deviceNameCn);
        item.add(deviceFather);
        item.add(repairUser);
        item.add(faultReport);
        item.add(position);
        for (int i = 0; i < item.size(); i++) {
            if (item.get(i).equals("") || item.get(i).isEmpty()) {
                isNull = false;
                switch (i) {
                    case 0: {
                        ed_reportPerson.setHint(notNull);
                        ed_reportPerson.setHintTextColor(Color.RED);
                    }

                    break;
                    case 1: {
                        ed_phone.setHint(notNull);
                        ed_phone.setHintTextColor(Color.RED);
                    }

                    break;
                    case 2: {
                        ed_deviceNameCn.setHint(notNull);
                        ed_deviceNameCn.setHintTextColor(Color.RED);
                    }

                    break;
                    case 3: {
                        ed_deviceFather.setHint(notNull);
                        ed_deviceFather.setHintTextColor(Color.RED);
                    }

                    break;
                    case 4: {
                        ed_repairUser.setHint(notNull);
                        ed_repairUser.setHintTextColor(Color.RED);
                    }

                    break;
                    case 5: {
                        ed_faultReport.setHint(notNull);
                        ed_faultReport.setHintTextColor(Color.RED);
                    }
                    break;
                    case 6: {
                        ed_position.setHint(notNull);
                        ed_position.setHintTextColor(Color.RED);
                    }

                    break;
                    default:
                        break;
                }
                // return false;
            } else
                continue;
        }
        return isNull;
    }

    private void Addrepair() {
        // TODO Auto-generated method stub

        final String reportPerson = ed_reportPerson.getText().toString().trim();
        final String phone = ed_phone.getText().toString().trim();
        final String deviceNameCn = ed_deviceNameCn.getText().toString().trim();
        final String deviceFather = ed_deviceFather.getText().toString().trim();
        final String faultReport = ed_faultReport.getText().toString().trim();
        final String repairUser = ed_repairUser.getText().toString().trim();
        final String position = ed_position.getText().toString().trim();
        // 检查是否填写数据完整
        boolean isDone = isDoneInput(reportPerson, phone, deviceNameCn,
                deviceFather, repairUser, faultReport, position);

        if (!isDone) {
            Toast.makeText(getApplicationContext(), "请将数据填写完整",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!StringUtils.checkPhone(phone) || phone.length() != 11) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog();
        if (checkMode() == Constant.NETWORKOFF) {
            // showDialog

            List<Map<String, Object>> locData = new ArrayList<Map<String, Object>>();
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("reportPersonNew", reportPerson);
            item.put("telephoneNew", phone);
            item.put("receptionUserNew", MyApplication.userInfo.getUserName());
            item.put("deviceNameCnNew", deviceNameCn);
            item.put("deviceIdNew", deviceId);
            item.put("deviceFatherNew", deviceFather);
            item.put("faultReportNew", faultReport);
            item.put("repairUserNew", repairUser);
            item.put("disposeTimeNew", DateUtils.getDate().toString());
            item.put("statusNew", "0");
            item.put("OperateType", "");
            item.put("Operator", "");
            item.put("OperateTime", "");
            item.put("orderCodeNew", "BX" + deviceId);
            locData.add(item);
            AddToLoc(locData);
            toSearch();
            ToastUtil.showToast(getApplicationContext(), "添加成功");
            dialogDismiss();

            return;
        }

        MaintenanceOrderProperty request = new MaintenanceOrderProperty();

        request.reportPerson = reportPerson;// 报修人
        request.telephone = phone;// 报修电话
        request.receptionUser = "";// 接待人
        request.deviceNameCn = deviceNameCn;// 报修设备名称
        request.building = deviceFather;// 设备所属建筑
        request.faultReport = faultReport;// 故障描述
        request.repairUser = repairUser;// 指派维修人员
        request.position = position;// 安装位置
        request.deviceId = deviceId;// 设备id
        request.status = 0;
        request.id = 0;
        request.disposeTime = DateUtils.getDate().toString();
        request.orderCode = "BX" + deviceId;
        request.userAccountName = application.userInfo.getAccount();

        String param = JsonTools.StringToJson_Back(request);
        http(ADDRESS_ADD, 1, param, new MyStringCallback());
    }

    /**
     * 将数据添加到本地
     *
     * @param locData
     */
    private void AddToLoc(List<Map<String, Object>> locData) {
        // TODO Auto-generated method stub
        DBUtils dbu = new DBUtils(initApplication());
        try {
            dbu.insertRepair(locData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request,int code) {
            super.onBefore(request,code);
            // setTitle("loading...");
            showDialog();
        }

        @Override
        public void onAfter(int code) {
            super.onAfter(code);
            // setTitle("Sample-okHttp");
            dialogDismiss();
        }

        @Override
        public void onError(Call call, Exception e,int code) {
            // mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response,int code) {
            // 返回的结果
            checkTemplateMessage = (CheckTemplateMessage) JsonTools
                    .JsonToStruts(response, CheckTemplateMessage.class);
            if (checkTemplateMessage.responseCommand.equals("OK")) {
                ToastUtil.showToast(getApplicationContext(), "添加成功");
                toSearch();
            } else {
                ToastUtil.showToast(getApplicationContext(), "添加失败");
            }
        }

//        @Override
//        public void inProgress(float progress) {
//            // Log.e(TAG, "inProgress:" + progress);
//            // mProgressBar.setProgress((int) (100 * progress));
//        }
    }

    private void toSearch() {
        Intent intent = new Intent();
        intent.setClass(AddRepairActivity.this, RepairSearchActivity.class);
        startActivity(intent);
        finish();
    }

}
