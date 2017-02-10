package com.zchk.yunzichan.ui.activity.login;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.bean.Device;
import com.zchk.yunzichan.entity.bean.UserInfo;
import com.zchk.yunzichan.entity.bean.check.DeviceList;
import com.zchk.yunzichan.entity.model.RequestAndResponse;
import com.zchk.yunzichan.entity.model.login.WeixinGetNavbarPathMessage;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.main.MainActivity;
import com.zchk.yunzichan.utils.Encrypt;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by SenseTech on 2016/9/18.
 */
public class Login_Activity extends BaseActivity {
    private static String url = GlobalDefine.CmdPath.cmdPath
            + "APPLoginCmd";

    private static final String TAG = "Login_Activity";
    public Thread thread;
    public Handler handler;

    private WeixinGetNavbarPathMessage persons;

    private EditText edt_userName;
    private EditText edt_pass;
    private Button btn_login;
    RequestAndResponse responseCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);

        initView();
        initEven();
        hideTopBar();
    }

    private void initView() {
        // TODO Auto-generated method stub
        edt_userName = (EditText) findViewById(R.id.edt_username);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        String username = share.getLastUser(Login_Activity.this);
        edt_userName.setText(username);
    }

    private void initEven() {
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                initThread();
            }
        });
    }

    public void initThread() {
        // 在这里进行 http request.网络请求相关操作
        WeixinGetNavbarPathMessage request = new WeixinGetNavbarPathMessage();
        request.userName = Encrypt.getBase64(edt_userName.getText().toString().trim());
        request.password = Encrypt.getBase64(edt_pass.getText().toString().trim());

        String param = JsonTools.StringToJson_Login(request);
        http(url, 1, param, new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showDialog("正在登陆，请稍后...");
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dialogDismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(Login_Activity.this, "网络错误！请检查网络设置或稍后重试");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                dealData(response);
            }
        });
    }

    @SuppressLint("NewApi")
    protected void dealData(String response) {
        // TODO Auto-generated method stub
        Log.e(TAG,"what's the matter?"+response);
        if (response.isEmpty() || response.equals("")) {
            ToastUtil.showToast(Login_Activity.this, "未知错误");
            return;
        }
        responseCmd = parseString(response);
        if (responseCmd.responseCommand.equals("Fail")) {
            ToastUtil.showToast(getApplicationContext(), "用户名或密码不正确");
            return;
        } else {
            persons = (WeixinGetNavbarPathMessage) JsonTools.JsonToStruts(response, WeixinGetNavbarPathMessage.class);
            loginSuccess();
        }
    }

    private void loginSuccess() {
        Log.e(TAG, "loginSuccess");
        // 保存登录信息
        saveLoginInfo();

        IntentToWords();

        finish();

    }

    private void IntentToWords() {

        Intent intent = new Intent();
        intent.putExtra("UserName", edt_userName.getText().toString().trim());
        intent.setClass(Login_Activity.this, MainActivity.class);
        startActivity(intent);
        // 设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 保存登录信息
     */
    private SharedPreferences sp;

    private void saveLoginInfo() {
        Log.e(TAG, "saveLoginInfo");
        // 下面将user信息保存到缓存里面
        String account = edt_userName.getText().toString().trim();
        String password = edt_pass.getText().toString().trim();
        UserInfo u = new UserInfo();
        u.setCompanyId(persons.companyId);
        u.setRoleId(Integer.valueOf(persons.role));
        u.setAccount(account);
        u.setPassword(password);
        u.setRoleName(persons.roleName);
        u.setUserName(persons.userName);
        MyApplication.userInfo = u;
        //将用户信息保存到sp,进行长期保存
        sp = getSharedPreferences("UserCache", MODE_PRIVATE);
        sp.edit().putString("userName", persons.userName).commit();//用户名
        sp.edit().putString("roleName", persons.roleName).commit();//职位
        sp.edit().putString("password", password).commit();//将用户密码保存到本地，便于修改密码时进行比对
        // 将登录的用户信息添加到缓存里面
        share.setUserInfo(Login_Activity.this, account, password, persons.roleName, persons.userName);
        DeviceList deviceList = new DeviceList();
        List<Device> list = new ArrayList<>();
        Device device = null;
        for (int i = 0; i < persons.deviceItems.length; i++) {
            device = new Device();
            device.setNameCn(persons.deviceItems[i].nameCn);
            device.setTypeName(persons.deviceItems[i].typeCode);
            Log.e(TAG,"typeCode:"+persons.deviceItems[i].typeCode);
            list.add(device);
        }

        //这两句代码必须的，为的是初始化出来gson这个对象，才能拿来用
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type type = new TypeToken<List<Device>>() {
        }.getType();
        String jsonListTest = gson.toJson(list, type);
        Log.e(TAG,jsonListTest);
        // 设施登录状态为true
        share.setDeviceInfo(jsonListTest, Login_Activity.this);
        share.setLoginStatus(Login_Activity.this, true);
        share.setLastUser(Login_Activity.this, account);
    }

    private RequestAndResponse parseString(String respJson) {
        return (RequestAndResponse) JsonTools.JsonToStruts(respJson,
                RequestAndResponse.class);
    }
}
