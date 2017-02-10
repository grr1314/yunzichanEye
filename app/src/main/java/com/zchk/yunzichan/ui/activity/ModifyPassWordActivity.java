package com.zchk.yunzichan.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.model.modifypassword.ModifyPWRequest;
import com.zchk.yunzichan.entity.model.modifypassword.ModifyPWResponse;
import com.zchk.yunzichan.ui.activity.login.Login_Activity;
import com.zchk.yunzichan.ui.activity.main.MainActivity;
import com.zchk.yunzichan.ui.activity.tools.luoji.MyAdapter;
import com.zchk.yunzichan.ui.fragment.main.Me_Fragment;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by admin on 2017/1/10.
 * 此界面是用户修改密码的界面
 *
 */
public class ModifyPassWordActivity extends BaseActivity implements View.OnClickListener{

    private static final int RESULT_FOR_MODIFY =100 ;
    private EditText etPassword;//新密码
    private EditText etPassword2;//确认密码
    private Button btnConfirm;
    private Button btnCancle;
    private SharedPreferences sp;
    private String oldPassword;
    private TextInputLayout textInputLayout2;
    private String passWord;
    private String passWord2;
    private static String url = GlobalDefine.CmdPath.cmdPath
            + "DevOpsAccountUpdateCmd";
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        initViews();
        initListener();
    }

    private void initViews() {
        // TODO Auto-generated method stub
        initTopBar("修改密码", true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        etPassword = (EditText) findViewById(R.id.edt_newpassword);
        etPassword2 = (EditText) findViewById(R.id.edt_newpassword2);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.textInputLayout2);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnConfirm.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        //获取老的密码（此密码是在登录界面进行保存的）
        sp = getSharedPreferences("UserCache", MODE_PRIVATE);
        oldPassword = sp.getString("password","");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm://点击确认按钮
                passWord = etPassword.getText().toString().trim();
                passWord2 = etPassword2.getText().toString().trim();
                if(TextUtils.isEmpty(passWord)){
                    Toast.makeText(this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passWord2)){
                    Toast.makeText(this,"请再次确认密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passWord.equals(passWord2)){
                    Toast.makeText(this,"两次密码不一致,请重新输入！",Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPassword2.setText("");
                    return;
                }
                if(passWord.equals(oldPassword)){
                    Toast.makeText(this,"密码未改动,请重新输入！",Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPassword2.setText("");
                    return;
                }
                //下面进行真正的网络操作，提交新的密码修改,此时弹出对话框进行提示
                initDialog("正在修改，请稍后...");
                initData();
                break;

            case R.id.btn_cancle://点击取消按钮
                finish();
                break;
        }
    }
    private static String USERINFO = "userInfo";
    private void initData() {

        SharedPreferences sp = getSharedPreferences(USERINFO,
                Context.MODE_PRIVATE);
        ModifyPWRequest request = new ModifyPWRequest();
        request.requestCommand = "";
        request.responseCommand = "";
        request.id = -1;
        request.PWD = passWord2;
        request.account = sp.getString("account","");
        request.personName=sp.getString("userName","");
        request.userPicture = "";
        request.viewRoleId = -1;
        request.OrganizationId = -1;
        request.quartersId = -1;
        request.insertTime = "";
        request.updateTime = "";
        request.userAccountName =sp.getString("account","");
        String param = JsonTools.StringToJson_ModifyPassWord(request);
        //请求网络数据
        http(url, 1, param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                dialogDismiss();
                ToastUtil.showToast(ModifyPassWordActivity.this, "网络错误，请检查网络状态！");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("csh", "onResponse: "+response);
                Gson gson = new Gson();
                //生成一个对象
                ModifyPWResponse modifyPWResponse = gson.fromJson(response, ModifyPWResponse.class);
                if(modifyPWResponse==null){
                    return;
                }
                if ("OK".equals(modifyPWResponse.responseCommand)) {
                    ToastUtil.showToast(ModifyPassWordActivity.this, "修改成功，请重新登录！");//跳转到登录界面重新登录
                    Intent data = new Intent(ModifyPassWordActivity.this,Me_Fragment.class);
                    // 结果就给A了
                    setResult(RESULT_FOR_MODIFY, data);
                    finish();
                    dialogDismiss();
                } else {
                    dialogDismiss();
                    // show Error Page
                    ToastUtil.showToast(ModifyPassWordActivity.this, "查询失败");
                }
            }
        });
    }

    private void initListener() {
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<=3&&s.length()>=1){
                    textInputLayout2.setErrorEnabled(true);
                    textInputLayout2.setError("密码长度太短，建议修改");
                }else{
                    textInputLayout2.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                passWord = etPassword.getText().toString().trim();
            }
        });

        etPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                passWord2 = etPassword2.getText().toString().trim();
                if(!passWord.equals(passWord2)){
                    textInputLayout2.setErrorEnabled(true);
                    textInputLayout2.setError("密码不一致");
                }else{
                    textInputLayout2.setErrorEnabled(false);
                }
            }
        });
    }

    /**
     * 用于显示Dialog
     */
    public void showDialog() {
        if (mDialog != null) {
            showDialogMethod();
            return;
        }
        initDialog(null);
    }

    /**
     * 显示Dialog
     *
     * @param message
     */
    public void showDialog1(String message) {
        initDialog(message);
    }

    private void showDialogMethod() {
        mDialog.show();
    }

    public void dialogDismiss() {
        if (mDialog == null) {
            return;
        }
        mDialog.dismiss();
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    private void initDialog(String message) {
        // TODO Auto-generated method stub
        if (message == null || message.equals("")) {
            message = "正在加载，请稍后...";
        }
        mDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        mDialog.setMessage(message);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        showDialogMethod();
    }
}
