package com.zchk.yunzichan.ui.activity.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.model.checkroute.PersonalTaskRouteMessage;
import com.zchk.yunzichan.entity.model.checkroute.PersonalTaskRouteQueryMessage;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.route.routePlanActivity;
import com.zchk.yunzichan.ui.adapter.CheckAllRouteAdapter;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 巡检自查界面
 *
 * @author SenseTech
 */
public class HomeSelfCheckActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String url = GlobalDefine.CmdPath.cmdPath
            + "APPPersonalTaskRouteSearchCmd";

    private ListView ls_allRoute;

    private CheckAllRouteAdapter adapter;

    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Map<String, Object> item;

    private PersonalTaskRouteQueryMessage request;
    private PersonalTaskRouteMessage routeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_self_activity);
        initViews();
    }

    private void initViews() {

        initTopBar("任务路线", true, false, true);
        initTopBarListener(null, null, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ls_allRoute = (ListView) findViewById(R.id.ls_allRoute);

        adapter = new CheckAllRouteAdapter(HomeSelfCheckActivity.this, list);
        ls_allRoute.setAdapter(adapter);
        ls_allRoute.setOnItemClickListener(this);
        initData();
    }

    private void initData() {

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
                //刷新list
                for (int i = 0; i < routeList.item.length; i++) {
                    item=new HashMap<>();
                    item.put("routeId", routeList.item[i].routeId);
                    item.put("routeName", routeList.item[i].routeName);
                    list.add(item);
                }
                putData();
            }
        }
        putData();
    }

    protected void putData() {
        adapter.notifyDataSetChanged();
    }

    private PersonalTaskRouteMessage parseString(String json) {
        return (PersonalTaskRouteMessage) JsonTools.JsonToStruts(json,
                PersonalTaskRouteMessage.class);
    }

    private String buildRequest() {
        // TODO Auto-generated method stub
        request = new PersonalTaskRouteQueryMessage();
        request.userId = -1;
        request.userAccountName = MyApplication.userInfo.getAccount();
        return JsonTools.StringToJson_CheckRoute(request);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(HomeSelfCheckActivity.this, routePlanActivity.class);
        intent.putExtra("routeId", (Integer) list.get(position).get("routeId"));
        intent.putExtra("routeName",list.get(position).get("routeName").toString());
        startActivity(intent);
    }
}

