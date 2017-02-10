package com.zchk.yunzichan.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.receiver.NetWorkCheckReceiver;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.HttpMethods;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by SenseTech on 2016/9/18.
 */
public class BaseActivity extends AppCompatActivity {
    RelativeLayout parentLayout;
    // LinearLayout viewStubLayout;
    TextView tv_title;
    TextView btn_addRepair;
    ImageView iv_back;
    LinearLayout ll_back;

    MyApplication application;
    RelativeLayout topbar;
    NetWorkCheckReceiver netWorkCheck;

    ViewGroup viewGroup;

    // NetWorkUtils net;
    ViewStub vs_error;
    ViewStub vs_loding;
    ViewStub vs_empty;

    // 加载框
    ProgressDialog mDialog;

    private boolean isRegisterReceiver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        initStausBar();
        setMyContentView();

        if (!isRegisterReceiver) {
            initNetCheckListener();
            isRegisterReceiver = true;
        }
        Log.e("BaseActivity", "" + isRegisterReceiver);
    }

    private void setMyContentView() {
        // TODO Auto-generated method stub
        // 注意：这里的android.R.id.content是DecorView子View的id，也就是我们平时写的布局的上一层布局
        viewGroup = (ViewGroup) findViewById(android.R.id.content);
        parentLayout = new RelativeLayout(this);
//        parentLayout.setOrientation(LinearLayout.VERTICAL);

        // 添加公用的错误界面
        initSetErrorView(R.layout.error_view);


    }

    /**
     * 实现LodingView的共享
     */
    private void initSetLodingView(int lodingView) {
        // TODO Auto-generated method stub
        viewGroup.removeAllViews();
        viewGroup.addView(parentLayout);
        LayoutInflater.from(this).inflate(lodingView, parentLayout, true);
    }

    /**
     * 实现errorView的共享
     *
     * @param errorView
     */
    private void initSetErrorView(int errorView) {
        // TODO Auto-generated method stub
        viewGroup.removeAllViews();
        viewGroup.addView(parentLayout);
        LayoutInflater.from(this).inflate(errorView, parentLayout, true);
    }

    /**
     * 重写setContentView方法以实现布局的共享
     *
     * @param topbar
     */
    private void initSetToolBar(int topbar) {
        // TODO Auto-generated method stub
        viewGroup.removeAllViews();
        viewGroup.addView(parentLayout);
        LayoutInflater.from(this).inflate(topbar, parentLayout, true);
    }

    public void setTopbarBack(int background)
    {
        topbar.setBackgroundColor(background);
    }

    /**
     * 初始化错误界面
     */
    private void initErrorPage() {
        vs_error = (ViewStub) findViewById(R.id.vs_error);
        vs_error.inflate();
    }

    /**
     * 当界面网络加载出线问题，则显示加载错误界面
     */
    public void showErrorPage() {
        initErrorPage();
        TextView tv_error = (TextView) findViewById(R.id.tv_error);
        tv_error.setText("当前网络不稳，请稍后重试!");
    }

    private void initLodingPage() {
        vs_loding = (ViewStub) findViewById(R.id.vs_loding);
    }

    public void showLodingPage() {
        initLodingPage();
        vs_loding.inflate();
    }

    private void initEmptyPage()
    {
        vs_empty= (ViewStub) findViewById(R.id.vs_empty);
    }

    public void showEmptyPage()
    {
        initEmptyPage();
        vs_loding.inflate();
    }

    private void initNetCheckListener() {
        netWorkCheck = new NetWorkCheckReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction("android.intent.action.DOWNLOAD_COMPLETED");// 添加这个action可以解决onReceive执行多次的问题
        registerReceiver(netWorkCheck, intentFilter);
    }

    /**
     * 实例化Application对象
     *
     * @return
     */
    public MyApplication initApplication() {
        application = (MyApplication) getApplication();
        return application;
    }

    /**
     * 初始化共有的控件
     */
    private void initTopBarViews() {
        // TODO Auto-generated method stub
        topbar = (RelativeLayout) findViewById(R.id.topbar);
        if (topbar.getVisibility() == View.GONE) {
            topbar.setVisibility(View.VISIBLE);
        }
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_addRepair = (TextView) findViewById(R.id.btn_addRepair);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
    }

    private void initStausBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
    /**
     * 配置topbar
     *
     * @param title
     * @param b     为true表示tv_title显示，false表示不现实
     * @param b1    为true表示btn_addRepair显示，false表示不现实
     * @param b2    为true表示iv_back显示，false表示不现实
     */
    public void initTopBar(String title, boolean b, boolean b1, boolean b2) {
        if (!title.equals("")) {
            if (checkMode() == Constant.NETWORKOFF) {
                tv_title.setText(title + "（离线）");
            } else
                tv_title.setText(title);
        }
        if (b) {
            tv_title.setVisibility(View.VISIBLE);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        if (b1) {
            btn_addRepair.setVisibility(View.VISIBLE);
        } else {
            btn_addRepair.setVisibility(View.GONE);
        }
        if (b2) {
            ll_back.setVisibility(View.VISIBLE);
        } else {
            ll_back.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化topbar的view的点击事件
     *
     * @param lisTitle
     * @param lisAdd
     * @param lisBack
     */
    public void initTopBarListener(View.OnClickListener lisTitle,
                                   View.OnClickListener lisAdd, View.OnClickListener lisBack) {
        if (lisTitle != null) {
            tv_title.setOnClickListener(lisTitle);
        }
        if (lisAdd != null) {
            btn_addRepair.setOnClickListener(lisAdd);
        }
        if (lisBack != null) {
            // iv_back.setOnClickListener(lisBack);
            ll_back.setOnClickListener(lisBack);
        }
    }

    /**
     * 隐藏topbar
     */
    public void hideTopBar() {
        topbar.setVisibility(View.GONE);
    }

    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
        // 重写setContentView方法以实现布局的共享
        initSetToolBar(R.layout.topbar);
        initTopBarViews();
    }

    @Override
    public void setContentView(View view) {

        parentLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

        parentLayout.addView(view, params);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(netWorkCheck);
    }

    // 如何判断当前处于哪种模式呢？
    protected int checkMode() {
        // 检查当前处于哪种模式
        int mode = share.getMode(BaseActivity.this);
        return mode;
    }

    /**
     * 设置app的状态
     *
     * @param mode
     */
    public void setNetMode(int mode) {
        share.setMode(BaseActivity.this, mode);
    }

    /**
     * 公用的访问网络的方法
     *
     * @param url  访问网络所使用的ip
     * @param type 访问网络的方法类型GET或POST
     */
    public String http(String url, int type, String param, Callback call) {
        // 考虑这个地方是用线程呢还是用线程池呢，还有一个问题就是如果出现访问两个接口的问题怎么处理

        switch (type) {
            case 0: {
                // GET
                HttpMethods.getString(url, param, call);
            }
            break;
            case 1: {
                // Post
                HttpMethods.postString(url, param, call);
            }
            break;
            default:
                break;
        }

        return "";
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
    public void showDialog(String message) {
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
