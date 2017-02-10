package com.zchk.yunzichan.ui.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.db.DatabaseHelper;
import com.zchk.yunzichan.entity.bean.UserInfo;
import com.zchk.yunzichan.entity.model.update.UpdateByVersionRequest;
import com.zchk.yunzichan.entity.model.update.UpdateByVersionResponse;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.fragment.main.FirstPage_Fragment;
import com.zchk.yunzichan.ui.fragment.main.Me_Fragment;
import com.zchk.yunzichan.ui.fragment.main.System_Fragment;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;

    private TextView id_tab_fristPage;
    private TextView id_tab_Set;
    private TextView id_tab_Me;

    // 接收刷新ui的Receiver
    UpdateUIReceiver receiver;

    // 用于判断是否退出
    boolean isExit = false;
    Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            super.handleMessage(msg);
            isExit = false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upDate();
        receiver = new UpdateUIReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter
                .addAction("com.sensetech.yunzichanst.receiver.UpdateUIReceiver");
        registerReceiver(receiver, intentFilter);
        initView();
        initEvent();
        setSelect(0);

        int mode = checkMode();// 用于检测当前处于哪种模式
        if (mode == Constant.NETWORKONLINE) {// 表示在线模式
            // 开始启动轮询
//			PollingUtils.startPollingService(this, 5, CheckServerService.class,
//					CheckServerService.ACTION);
            Toast.makeText(getApplicationContext(), "当前处于在线状态",
                    Toast.LENGTH_SHORT).show();
        } else {// 表示离线模式
            Toast.makeText(getApplicationContext(), "当前处于离线状态",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        initTopBar("资产物联管理平台", true, false, false);
    }

    private void initView() {
        mTabWeixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
        mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);

        mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
        mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);

        id_tab_fristPage = (TextView) findViewById(R.id.id_tab_fristPage);
        id_tab_Set = (TextView) findViewById(R.id.id_tab_Set);
        id_tab_Me = (TextView) findViewById(R.id.id_tab_Me);

        // 初始化topbar
        initTopBar("资产物联管理平台", true, false, false);
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为暗色的
        resetImgs();
        // 将文字设置为暗色的
        resetTxt();
        // 设置内容区域
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new FirstPage_Fragment();
                    transaction.add(R.id.id_content, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                mImgWeixin.setImageResource(R.mipmap.btmhomeactive);
                id_tab_fristPage.setTextColor(Color.parseColor("#0084D0"));
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new System_Fragment();
                    transaction.add(R.id.id_content, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                mImgFrd.setImageResource(R.mipmap.btmsetactive);
                id_tab_Set.setTextColor(Color.parseColor("#0084D0"));
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new Me_Fragment();
                    transaction.add(R.id.id_content, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                mImgAddress.setImageResource(R.mipmap.btmuseractive);
                id_tab_Me.setTextColor(Color.parseColor("#0084D0"));
                break;
            default:
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_weixin:
                setSelect(0);
                break;
            case R.id.id_tab_frd:
                setSelect(1);
                break;
            case R.id.id_tab_address:
                setSelect(2);
                break;
            default:
                break;
        }
    }

    /**
     * 切换图片至暗色
     */
    private void resetImgs() {
        mImgWeixin.setImageResource(R.mipmap.btmhome_unactive);
        mImgFrd.setImageResource(R.mipmap.btmset_unactive);
        mImgAddress.setImageResource(R.mipmap.btmuser_unactive);
    }

    private void resetTxt() {
        id_tab_fristPage.setTextColor(Color.parseColor("#909090"));
        id_tab_Set.setTextColor(Color.parseColor("#909090"));
        id_tab_Me.setTextColor(Color.parseColor("#909090"));
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     * 刷新UI
     */
    public void UI_Update(int pos) {
        // TODO Auto-generated method stub
        // 刷新Activity的UI
        update_UIActivity();
        // 刷新Fragment的UI
        updata_UIFragment();
    }

    /**
     * 刷新Fragment的界面
     */
    private void updata_UIFragment() {
        // TODO Auto-generated method stub
        ((FirstPage_Fragment) mTab01).updateUI();
    }

    /**
     * 刷新Activity的界面
     */
    private void update_UIActivity() {
        // TODO Auto-generated method stub
        View view = findViewById(R.id.view_frist);
        view.setVisibility(View.VISIBLE);
    }

    public UserInfo getUser() {
        return initApplication().userInfo;
    }

    public class UpdateUIReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // 拿到进度，更新UI
            int progress = intent.getIntExtra("progress", -1);
            Log.e("UpdateUIReceiver", "" + progress);
            UI_Update(progress);

        }

    }

    public int FragmentCheckNetwork() {
        return checkMode();

    }

    public void FragmentShowDialog() {
        showDialog();
    }

    public void FragmentDismissDialog() {
        dialogDismiss();
    }

    /**
     * 按物理返回键,该方法将被调用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast(getApplicationContext(), "再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //更新数据库的操作
    public static int getMax(int a, int b) {
        return a > b ? a:b;
    }
    private SharedPreferences sp;
    DatabaseHelper helper;
    private SQLiteDatabase db;
    private int dbVersion;
    private static String url_update = GlobalDefine.CmdPath.cmdPath
            + "AppUpdateByVersionCmd";
    private void upDate() {
        sp = getSharedPreferences("CSHCache", MODE_PRIVATE);
        int v= 1;//取出保存到sp中的v
        v = sp.getInt("max",v);
        UpdateByVersionRequest request = new UpdateByVersionRequest();
        request.version = v;
        final String param = JsonTools.StringToJson_Update(request);
        http(url_update, 1, param, new StringCallback() {
            private int version1;
            private int version2;
            private String item2;
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(String response, int id) {
                Log.i("csh", "升级数据库: "+response);
                Gson gson = new Gson();
                final UpdateByVersionResponse UpdateByVersionResponse =gson.fromJson(response, UpdateByVersionResponse.class);//生成一个对象
                if ("OK".equals(UpdateByVersionResponse.resp.responseCommand)) {
                    if(UpdateByVersionResponse.item==null){
                        return;
                    }
                    for (int i = 0; i <UpdateByVersionResponse.item.length ; i++) {
                        version1 = UpdateByVersionResponse.item[i].version;
                        if(i<UpdateByVersionResponse.item.length-1){
                            //TODO判断是否有误
                            version2 = UpdateByVersionResponse.item[i+1].version;
                        }//判断返回的版本号中的较大值
                        int max =getMax(version1,version2);
                        sp.edit().putInt("max", max).commit();
                        if(UpdateByVersionResponse.item[i].type.equals("insert")){//如果是插入数据
                            helper = new DatabaseHelper(getApplicationContext(), "DevOpsDevelop1",UpdateByVersionResponse.item[i].version,null,null);//更新数据库版本,注意版本号必须是不断增加的
//                          helper = new DatabaseHelper(getApplicationContext(), "DevOpsDevelop1",9,arr);//更新数据库版本,注意版本号必须是不断增加的
                            SQLiteDatabase db  = helper.getWritableDatabase();
                            Object []item1 = UpdateByVersionResponse.item[i].colValue.split("#");//后台数据有问题暂时屏蔽
                            db.execSQL("insert into "+UpdateByVersionResponse.item[i].tableCn+"("+UpdateByVersionResponse.item[i].colCn+")values("+UpdateByVersionResponse.item[i].format+")",item1);
//                            db.execSQL("insert into "+UpdateByVersionResponse.item[i].tableCn+"("+UpdateByVersionResponse.item[i].colCn+")values(?,?,?,?,?,?,?,?)",item1);
//							db.execSQL("insert into checktemplete(templeteCode,nameCn,nameEn,viewType,defaultValue,allValue,sign,dataType)values(?,?,?,?,?,?,?,?)",new Object[]{"SenseXGD","异常备注","CheckPointa","1","无异常","无异常,需要清洗","0","2"});
                        }else if(UpdateByVersionResponse.item[i].type.equals("create")){
                            ArrayList<String> arr  = new ArrayList<String>();
                            arr.add(UpdateByVersionResponse.item[i].colValue);
                            helper = new DatabaseHelper(getApplicationContext(), "DevOpsDevelop1",UpdateByVersionResponse.item[i].version,arr,null);//更新数据库版本,注意版本号必须是不断增加的
//                          helper = new DatabaseHelper(getApplicationContext(), "DevOpsDevelop1",9,arr);//更新数据库版本,注意版本号必须是不断增加的
                            SQLiteDatabase db  = helper.getWritableDatabase();
                        }else if(UpdateByVersionResponse.item[i].type.equals("alert")){
                            ArrayList<String> arr1  = new ArrayList<String>();
                            arr1.add(UpdateByVersionResponse.item[i].colValue);
                            helper = new DatabaseHelper(getApplicationContext(), "DevOpsDevelop1",UpdateByVersionResponse.item[i].version,null,arr1);
                            SQLiteDatabase db  = helper.getWritableDatabase();
                        }
                    }
//					db.close();
                } else {
                    // show Error Page
                    ToastUtil.showToast(getApplicationContext(), "查询失败");
                }
            }
        });
    }


}
