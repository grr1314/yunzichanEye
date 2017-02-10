package com.zchk.yunzichan.ui.activity.check;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
//import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.RequestParams;
import com.yongchun.library.view.ImageSelectorActivity;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.entity.model.check.APPCheckInfoItem;
import com.zchk.yunzichan.entity.model.check.APPCheckInfoMessage;
import com.zchk.yunzichan.entity.model.check.AddCheckInfosMessage;
import com.zchk.yunzichan.entity.model.check.CheckByDeviceBasicInfoMessage;
import com.zchk.yunzichan.entity.model.check.CheckTemplateMessage;
import com.zchk.yunzichan.entity.model.check.TemplateInfos;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.adapter.CheckElevatorAdapter;
import com.zchk.yunzichan.ui.view.DeleteImageWindow;
import com.zchk.yunzichan.ui.view.NoScrollGridView;
import com.zchk.yunzichan.ui.view.NoScrollListView;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.DateUtils;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zchk.yunzichan.utils.StringUtils;
import com.zchk.yunzichan.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 显示并修改检点信息
 *
 * @author SenseTech
 */
@SuppressLint("NewApi")
public class CheckInsertActivity extends BaseActivity implements
        OnClickListener {
    private static final String TAG = "CheckInsertActivity";

    private static String urlId = GlobalDefine.CmdPath.cmdPath
            + "CheckInfosByDeviceIdAddCmd";
    // 获取最新一条数据的url
    private static String urllastOne = GlobalDefine.CmdPath.cmdPath
            + "CheckByDeviceBasicInfoCmd";

    private static String url = GlobalDefine.CmdPath.cmdPath
            + "GetCheckTemplateCmd";

    private static String urlInsert = GlobalDefine.CmdPath.cmdPath
            + "CheckInfosByTemplateAddCmd";

    private static String urlPic = GlobalDefine.CmdPath.cmdPath + "GetPicturesByCheckCmd";


    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    private String templateCode;// 点检设备的类型
    public Handler handler; // 查询最近一条

    public String deviceId;
    public String deviceName;
    public CheckByDeviceBasicInfoMessage persons;

    private CheckTemplateMessage checkTemplateMessage;

    public AddCheckInfosMessage localData;

    public TextView check_devicename;
    public TextView check_devicesnamecn;
    public TextView check_devicechangshang;
    public TextView check_devicetype;
    public TextView check_devicesno;

    public Button btn_choosePic;
    public NoScrollGridView gridview_imgs;

    private Button btn_add;
    private NoScrollListView check_lsview;

    private CheckElevatorAdapter adapter;
    private GridAdapter gridAdapter;
    private String taskId;

    DBUtils dbu;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<Bitmap> imageBiamap = new ArrayList<>();

    private List<TemplateInfos> list = new ArrayList<TemplateInfos>();

    private List<Map<String, Object>> listInfo;
    private String tempCode;

    private PopupWindow popupWindow;
    private View view;
    private int pos;
    List<Map<String, Object>> listDevice;
    DeleteImageWindow menuWindow;

    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_insert);
        initView();

        // 获取设备ID
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("ID");
        taskId = intent.getStringExtra("taskId");
        initListeners();
        initTopBarListener(null, this, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbu = new DBUtils(initApplication());
        if (checkMode() == Constant.NETWORKOFF) {
            initDataLoc();
            return;
        }
        // 加载数据
        initData();
    }

    /**
     * 初始化显示控件
     */
    public void initView() {
        check_devicename = (TextView) findViewById(R.id.check_devicename);
        check_devicechangshang = (TextView) findViewById(R.id.check_devicechangshang);
        check_devicetype = (TextView) findViewById(R.id.check_devicetype);
        check_devicesno = (TextView) findViewById(R.id.check_devicesno);
        check_lsview = (NoScrollListView) findViewById(R.id.check_lsview);
        btn_add = (Button) findViewById(R.id.btn_add);

        btn_choosePic = (Button) findViewById(R.id.btn_choosePic);
        gridview_imgs = (NoScrollGridView) findViewById(R.id.gridview_imgs);
        gridAdapter = new GridAdapter();
        gridview_imgs.setAdapter(gridAdapter);
    }

    /**
     * 加载本地数据
     */
    private void initDataLoc() {
        showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    // 查询本地数据
                    listDevice = dbu
                            .selectDeviceById(deviceId);
                    Log.e(TAG, listDevice.size() + "");
                    String d = dbu.selectAssetType(listDevice.get(0)
                            .get("typeName").toString());

                    Log.e(TAG, "type:" + d);
                    String tempCode = dbu.selectTempAndDev(d);
                    templateCode = tempCode;
                    Log.e(TAG, "tempCode:" + tempCode);
                    listInfo = dbu.selectTemplateInfo(tempCode.toLowerCase());

                    Message message = Message
                            .obtain(handlerData, 2, listDevice);
                    message.sendToTarget();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initListeners() {
        btn_add.setOnClickListener(this);
        btn_choosePic.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initTopBar("设备巡检", true, false, true);
    }

    /**
     * 获取网络数据
     */
    private void initData() {
        CheckTemplateMessage ctm = new CheckTemplateMessage();
        ctm.deviceId = deviceId;
        // 将数据打包成json字符串
        String param = JsonTools.StringToJson_CheckUP(ctm);
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
                dealData(response);
            }

            @Override
            public void onError(Call call, Exception e, int code) {
            }
        });
    }

    /**
     * 处理数据
     *
     * @param response
     */
    protected void dealData(String response) {
        // 首先返回的数据是空
        if (response.isEmpty() || response.equals("")) {
//            showErrorPage();
            return;
        }
        // 解析数据
        checkTemplateMessage = (CheckTemplateMessage) JsonTools.JsonToStruts(
                response, CheckTemplateMessage.class);

        // 获取数据失败
        if (checkTemplateMessage.responseCommand.equals("Fail")) {
//            showErrorPage();
            return;
        }
        if (checkTemplateMessage.responseCommand.toUpperCase().equals("OK")) {
            if (checkTemplateMessage.items == null || checkTemplateMessage.items.length == 0) {
                return;
            }
            templateCode = checkTemplateMessage.items[0].templateCode;
            // 查询该设备的最后一条点检信息
            initLastOne();
        }
    }

    Handler handlerData = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case 0: {
                    ToastUtil.showToast(getApplicationContext(), "加载失败！");
                    finish();
                }
                break;
                case 1: {
                    for (int i = 0; i < checkTemplateMessage.items.length; i++) {
                        list.add(checkTemplateMessage.items[i]);
                    }
                    adapter = new CheckElevatorAdapter(CheckInsertActivity.this,
                            list, checkTemplateMessage.items[0].templateCode);
                    check_lsview.setAdapter(adapter);
                }
                break;
                case 2: {
                    List<Map<String, Object>> ls = (List<Map<String, Object>>) msg.obj;
                    if (ls.size() == 0) {
                        ToastUtil.showToast(getApplicationContext(), "暂无该设备信息");
                        finish();
                        return;
                    }
                    check_devicename.setText(ls.get(0).get("nameCn").toString());
                    check_devicechangshang.setText(ls.get(0).get("company")
                            .toString());
                    check_devicetype.setText(ls.get(0).get("typeName").toString());
                    check_devicesno.setText(ls.get(0).get("lableCode").toString());

                    TemplateInfos temps = null;
                    for (int i = 0; i < listInfo.size(); i++) {
                        temps = new TemplateInfos();
                        temps.templateCode = listInfo.get(i).get("templateCode")
                                .toString();
                        temps.nameCn = listInfo.get(i).get("nameCn").toString();
                        temps.nameEn = listInfo.get(i).get("nameEn").toString();
                        temps.defaultValue = listInfo.get(i).get("defaultValue")
                                .toString();
                        if (listInfo.get(i).get("dataType").toString().equals("4")) {
                            temps.dataType = "select";
                        } else if (listInfo.get(i).get("dataType").toString()
                                .equals("1")) {
                            temps.dataType = "chart";
                        } else {
                            temps.dataType = "radio";
                        }

                        temps.allValue = listInfo.get(i).get("allValue").toString();
                        list.add(temps);
                    }

                    adapter = new CheckElevatorAdapter(CheckInsertActivity.this,
                            list, listInfo.get(0).get("templateCode").toString());
                    check_lsview.setAdapter(adapter);

                }
                break;
                case 3: {
                    ToastUtil.showToast(getApplicationContext(), "添加成功！");
                    finish();
                }
                break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 获取设备信息
     */
    public void initLastOne() {
        CheckByDeviceBasicInfoMessage userCheckrequ = new CheckByDeviceBasicInfoMessage();
        userCheckrequ.userName = MyApplication.userInfo.getAccount();
        userCheckrequ.deviceId = deviceId;

        // 将数据打包成json字符串
        String param = JsonTools.StringToJson_lastCheck(userCheckrequ);
        http(urllastOne, 1, param, new StringCallback() {

            @Override
            public void onAfter(int code) {
                super.onAfter(code);
                dialogDismiss();
            }

            @Override
            public void onResponse(String response, int code) {
                Log.e(TAG, response);
                dealDevData(response);
            }

            @Override
            public void onError(Call call, Exception e, int code) {
                dialogDismiss();
            }
        });
    }

    /**
     * 处理设备信息数据
     *
     * @param response
     */
    protected void dealDevData(String response) {
        if (response.isEmpty() || response.equals("")) {
            return;
        }
        persons = (CheckByDeviceBasicInfoMessage) JsonTools.JsonToStruts(
                response, CheckByDeviceBasicInfoMessage.class);
        if (persons.responseCommand.equals("Fail")) {
            return;
        } else {
            // 确定显示不同的模板
            handlerData.sendEmptyMessage(1);
            deviceName = persons.item[0].deviceName;
            putData();
        }
    }

    /**
     * 添加点检记录
     *
     * @param item
     */
    public void addcheckInfo(final APPCheckInfoItem[] item) {

        APPCheckInfoMessage addCheck = new APPCheckInfoMessage();
        addCheck.deviceId = deviceId;
        addCheck.userName = MyApplication.userInfo.getAccount();
        addCheck.templateCode = templateCode;
        addCheck.items = item;
        if (taskId != null)
            addCheck.requestCommand = taskId;

        time = StringUtils.cutPicTime(DateUtils.getTime());
        //图片路径
        addCheck.picPath = "/" + templateCode + "/" + deviceId + "/" + time + "/";//图片的路径
        String param = JsonTools.StringToJson_Check(addCheck);
        //		/模板编号/二维码编号/以该设备的第一张图片时间为准
        http(urlInsert, 1, param, new StringCallback() {

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
                Log.e(TAG, response);
                persons = (CheckByDeviceBasicInfoMessage) JsonTools
                        .JsonToStruts(response,
                                CheckByDeviceBasicInfoMessage.class);

                if (persons.responseCommand.toUpperCase().equals("OK")) {
                    uploadImg(images);
                    Log.e(TAG, "success");

                } else {
                    ToastUtil.showToast(getApplicationContext(), "添加失败了");
                }

            }

            @Override
            public void onError(Call call, Exception e, int code) {
                ToastUtil.showToast(getApplicationContext(), "添加失败");
            }
        });

    }

    private void uploadImg(List<String> tempPah) {

        File[] imgs = new File[tempPah.size()];

        String infomation = templateCode + "/" + deviceId + "/" + time + "/";
        for (int i = 0; i < tempPah.size(); i++) {
            imgs[i] = new File(tempPah.get(i));
        }

        RequestParams params = new RequestParams();
        try {
            params.put("picPath", infomation);
            params.put("img", imgs);
        } catch (FileNotFoundException e) {
            ToastUtil.showToast(this, "程序异常,请重试");
        }

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(urlPic, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                ToastUtil.showToast(CheckInsertActivity.this, "上传成功");
                toOther(CheckQueryActivity.class);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtil.showToast(CheckInsertActivity.this, "网络错误");
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                if (totalSize != 0) {
                    Float a = Float.valueOf(bytesWritten / totalSize * 100);
                    Log.e("progress", "jd::::" + a);
                }
            }
        });

    }

    /*
     * 设置view
     */
    private void putData() {
        // 问题就是lastCheckInfo是空
        check_devicename.setText(persons.item[0].deviceName);
        check_devicechangshang.setText(persons.item[0].company);
        check_devicesno.setText(deviceId);
        if (persons.item[0].deviceType == null
                && dbu.selectDeviceById(deviceId).size() == 0) {
            check_devicetype.setText("暂无消息");
            return;
        }
        check_devicetype.setText(persons.item[0].deviceType);
    }

    /**
     * 获取点击按模板中的数据
     *
     * @return
     */
    @SuppressWarnings("unused")
    private String[] getListViewData() {
        Spinner sp;
        EditText et;
        String strItem = null;
        boolean isError = false;
        String str[] = new String[check_lsview.getChildCount()];
        for (int i = 0; i < check_lsview.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) check_lsview.getChildAt(i);
            sp = (Spinner) layout.findViewById(R.id.sp_status);
            if (sp.getVisibility() != View.GONE) {
                strItem = sp.getSelectedItem().toString();
                if (checkMode()==Constant.NETWORKONLINE)
                if (sp.getSelectedItemPosition() != 0 && persons.item[0].deviceType.equals("新风机组") && !isError) {
                    isError = true;
                }
            } else {
                et = (EditText) layout.findViewById(R.id.ed_content);
                strItem = et.getText().toString().trim();
            }
            str[i] = strItem;
        }
        if (isError) {
            //表示新风机组有异常
            share.setError(this, true);
            share.setErrorName(this, persons.item[0].deviceName);
        }
        else
        share.clearError(this);
        return str;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add: {
                // 获取数据
                String str[] = getListViewData();
                APPCheckInfoItem[] temps;
                if (checkMode() == Constant.NETWORKOFF) {
                    showDialog();
                    temps = makeTemps(str);
                    insertDataLoc(temps);
                    return;
                }
                temps = makeTemps(str);
                addcheckInfo(temps);
            }
            break;
            case R.id.btn_choosePic: {
                ImageSelectorActivity.start(CheckInsertActivity.this, 9, ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
            }
            break;
            default:
                break;
        }
    }

    public APPCheckInfoItem[] makeTemps(String[] str) {
        // 添加数据
        APPCheckInfoItem[] temps = new APPCheckInfoItem[str.length];
        APPCheckInfoItem temp;
        for (int i = 0; i < temps.length; i++) {
            temp = new APPCheckInfoItem();
            temp.defaultValue = str[i];
            if (checkMode() == Constant.NETWORKOFF) {
                temp.nameEn = (String) listInfo.get(i).get("nameEn");
            } else
                temp.nameEn = checkTemplateMessage.items[i].nameEn;
            temps[i] = temp;
        }
        return temps;
    }

    /**
     * 将数据添加到本地
     *
     * @param temps
     */
    @SuppressWarnings("unused")
    private void insertDataLoc(final APPCheckInfoItem[] temps) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String name = "check_" + templateCode + "_roleid";

                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                Map<String, String> item;
                for (int i = 0; i < temps.length; i++) {
                    item = new HashMap<>();
                    item.put("name", temps[i].nameEn);
                    item.put("value", temps[i].defaultValue);
                    list.add(item);
                }
                dbu.insertCheck(name, list, listDevice.get(0).get("lableCode").toString());

                handlerData.sendEmptyMessage(3);
            }
        }).start();
    }

    /**
     * 跳转到其他界面
     */
    public void toOther(Class cls) {
        Intent intent = new Intent();
        intent.setClass(CheckInsertActivity.this, cls);
        startActivity(intent);
        // 设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            //所谓的处理逻辑的地方
            gridview_imgs.setVisibility(View.VISIBLE);
            btn_choosePic.setVisibility(View.GONE);

            ArrayList<String> backList = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            for (String a : backList) {
                images.add(a);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * 内部类
     */
    private class GridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (images.size() == 9) {
                return images.size();
            }
            return images.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CheckInsertActivity.this).inflate(R.layout.item_result, parent, false);
                mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            if (position == images.size()) {
                mViewHolder.imageView.setImageResource(R.mipmap.add);
                mViewHolder.imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != images.size()) {
                            return;
                        }
                        ImageSelectorActivity.start(CheckInsertActivity.this, 9 - images.size(), ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
                    }
                });
                //图片数量9张的时候
                if (position == 9) {
                    mViewHolder.imageView.setVisibility(View.GONE);
                }

            } else {
                Glide.with(CheckInsertActivity.this)
                        .load(new File(images.get(position)))
                        .override(100, 100)
                        .centerCrop()
                        .into(mViewHolder.imageView);

                mViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        pos = position;
                        if (pos == images.size()) {
                            return false;
                        }
                        //弹出窗口做删除操作
                        menuWindow = new DeleteImageWindow(CheckInsertActivity.this, itemsOnClick);
                        //显示窗口
                        menuWindow.showAtLocation(CheckInsertActivity.this.findViewById(R.id.gridview_imgs), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

                        return false;
                    }
                });

            }
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageView;
    }

    //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.tv_delete:
                    images.remove(pos);
                    gridAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }

    };
}