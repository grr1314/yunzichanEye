package com.zchk.yunzichan.ui.fragment.leader;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.bean.check.CheckMaintenanceSearchOneManager;
import com.zchk.yunzichan.entity.model.check.LeaderInspectionRequest;
import com.zchk.yunzichan.entity.model.check.LeaderInspectionResponse;
import com.zchk.yunzichan.ui.activity.leader.CheckLeaderDetailActivity;
import com.zchk.yunzichan.ui.activity.query.CheckAndMaintenanceQueryActivity;
import com.zchk.yunzichan.ui.adapter.Check_Maintenance_AdapterOne;
import com.zchk.yunzichan.ui.fragment.base.BaseFragment;
import com.zchk.yunzichan.ui.view.RefreshLayout;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zhy.http.okhttp.callback.StringCallback;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DeviceCheckFragment extends BaseFragment implements AdapterView.OnItemClickListener, RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "DeviceCheckFragment";

    private static String url = GlobalDefine.CmdPath.cmdPath
            + "ManagerCheckSearchByTemplateCmd";

    private String title;
    private String tempCode;
    private ListView check_maintenance_listonel;
    private CheckQueryFragment check;
    private CheckAndMaintenanceQueryActivity activity;
    private LeaderInspectionResponse persons;

    private Check_Maintenance_AdapterOne myAdapter;
    private List<CheckMaintenanceSearchOneManager> data = new ArrayList<>();
    private CheckMaintenanceSearchOneManager item = null;

    private LinearLayout loding_page;

    private TextView tv_Empty;
    private int start = 0;
    private int count = 10;

    private RefreshLayout swipeLayout;

    //表示是不是没有数据，默认为false
    private boolean isEmpty = false;
    private boolean isFrist=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.leader_devicecheck_fragment,
                container, false);
        Bundle bundle = getArguments();
        title = bundle.getString("titles");
        tempCode = bundle.getString("tempCode");

        initViews(view);
        setListener();
        return view;
    }

    private void setListener() {
        swipeLayout.setOnLoadListener(this);
        swipeLayout.setOnRefreshListener(this);
    }

    private void initViews(View view) {
        check_maintenance_listonel = (ListView) view
                .findViewById(R.id.check_maintenance_listone);
        myAdapter = new Check_Maintenance_AdapterOne(getActivity(), data);
        check_maintenance_listonel.setAdapter(myAdapter);
        loding_page = (LinearLayout) view.findViewById(R.id.loding_page);

        tv_Empty = (TextView) view.findViewById(R.id.tv_Empty);
        check_maintenance_listonel.setOnItemClickListener(this);
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
    }

    @Override
    protected void onLoad() {
        startLoad();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        start += 10;
        startLoad();
    }

    private void startLoad() {

        LeaderInspectionRequest userCheckrequ = new LeaderInspectionRequest();
        userCheckrequ.index = start;
        userCheckrequ.number = count;
        userCheckrequ.tplCode = tempCode;
        userCheckrequ.userAccountName = MyApplication.userInfo.getAccount();

        String param = JsonTools.StringToJson_AdminCheck(userCheckrequ);
        FragmentHttps(url, 1, param, new StringCallback() {

            @Override
            public void onBefore(Request request, int code) {
                super.onBefore(request, code);
            }

            @Override
            public void onAfter(int code) {
                super.onAfter(code);
                if (isEmpty) {
                    loding_page.setVisibility(View.GONE);
                    check_maintenance_listonel.setVisibility(View.GONE);
                    swipeLayout.setVisibility(View.GONE);
                    tv_Empty.setVisibility(View.VISIBLE);
                } else {
                    loding_page.setVisibility(View.GONE);
                    check_maintenance_listonel.setVisibility(View.VISIBLE);
                    swipeLayout.setVisibility(View.VISIBLE);
                }
                if (!isFrist)
                {
                    swipeLayout.setLoading(false);

                }
                isFrist=false;
            }

            @Override
            public void onResponse(String response, int code) {
                Log.e(TAG, response);
                dealData(response);
            }

            @Override
            public void onError(Call call, Exception e, int code) {
                //showErrorView
            }
        });
    }

    @SuppressLint("NewApi")
    protected void dealData(String response) {
        // TODO Auto-generated method stub
        if (response.isEmpty() || response.equals("")) {
            isEmpty = true;
            return;
        }
        persons = (LeaderInspectionResponse) JsonTools.JsonToStruts(
                response, LeaderInspectionResponse.class);
        if (persons.resp.responseCommand != null
                && persons.resp.responseCommand.equals("OK")) {
            if (persons.item != null && persons.item.length != 0) {
                putData();
            }
        } else {
            //showEmptyView
            isEmpty = true;
            return;
        }
    }

    private void putData() {
        for (int i = 0; i < persons.item.length; i++) {
            item = new CheckMaintenanceSearchOneManager();
            item.setAccount(persons.item[i].checkUser);
            item.setDeviceID(persons.item[i].deviceName);
            item.setTime(persons.item[i].checkTime);
            item.setCheck(persons.item[i].item);
            data.add(item);
        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        data.clear();
        isFrist=true;
        start=0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("info", JsonTools.StringToJson_leaderCheck(data.get(position).getCheck()));
        intent.setClass(getActivity(), CheckLeaderDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
    }
}
