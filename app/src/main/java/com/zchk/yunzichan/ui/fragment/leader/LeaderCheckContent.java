package com.zchk.yunzichan.ui.fragment.leader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.model.check.DeviceClassifyInfoResponse;
import com.zchk.yunzichan.entity.model.leader.leaderCheckRequest;
import com.zchk.yunzichan.ui.activity.leader.ErrorRecordActivity;
import com.zchk.yunzichan.ui.activity.leader.LeaderCheckChartsActivity;
import com.zchk.yunzichan.ui.fragment.base.BaseFragment;
import com.zchk.yunzichan.ui.view.NoScrollListView;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by admin on 2016/11/25.
 */
public class LeaderCheckContent extends BaseFragment implements OnChartValueSelectedListener, AdapterView.OnItemClickListener {
    private String url = GlobalDefine.CmdPath.cmdPath + "DevOpsDeviceClassifyInfoSearchByEyeCmd";
    private static final String TAG = "LeaderCheckContent";
    private PieChart pieChartView;
    private PieData pieData;
    private Typeface typeface;
    private TextView tv_userName;

    private BarChart columnChartView;
    private BarData barData;

    private ScrollView scrollView;

    private LeaderCheckChartsActivity activity;

    private NoScrollListView ls_allUsers;
    private NoScrollListView ls_allError;

    private DeviceClassifyInfoResponse data = new DeviceClassifyInfoResponse();
    private List<Map<String, String>> listUser = new ArrayList<>();
    private List<Map<String, String>> tagList = new ArrayList<>();
    private Map<String, String> item;
    private int countPie;
    private int countUser;

    private List<String> labels = new ArrayList<>();

    private MyAdapter myAdapter;
    private MyAdapter myAdapter1;

    private LinearLayout ll_toLeaderCheck;
    private String[] tags = new String[]{"巡更点",
            "消防稳压泵房",
            "新风机组",
            "层间电气井",
            "污水泵",
            "电梯机房",
            "监控摄像机",
            "新型电梯机房"
    };
    private String[] names = new String[]{"马云飞", "李林"};
    private int[] tagCounts = new int[]{43,
            1,
            16,
            18,
            12,
            4,
            145
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leader_check_fragment, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    private void initListeners() {
    }

    private void initViews(View view) {
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);


        tv_userName= (TextView) view.findViewById(R.id.tv_userName);

        pieChartView = (PieChart) view.findViewById(R.id.pieChartView);

        ll_toLeaderCheck = (LinearLayout) view.findViewById(R.id.ll_toLeaderCheck);

        activity = (LeaderCheckChartsActivity) getActivity();
        initData();
        pieChartView.getDescription().setEnabled(false);
        pieChartView.setDragDecelerationFrictionCoef(0.95f);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");

        pieChartView.setUsePercentValues(false);
        pieChartView.setCenterTextTypeface(typeface);
        pieChartView.setCenterText("Hello");
        pieChartView.setCenterTextSize(18f);
        pieChartView.setCenterTextTypeface(typeface);
        pieChartView.setDrawEntryLabels(true);//绘制里面的文字
        pieChartView.setDrawHoleEnabled(false);//是否画中间的圆形，false表示不画
        pieChartView.setDrawCenterText(false);//是否绘制中间的文字，false表示不绘制
        pieChartView.animateX(1000);//设置以X轴为中心的动画
        pieChartView.setOnChartValueSelectedListener(this);
        /**
         * 控制颜色标志的位置，默认是在图形底部
         */
        Legend l = pieChartView.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        myAdapter = new MyAdapter(getActivity(), listUser);

        myAdapter1 = new MyAdapter(getActivity(), tagList);
    }

    private void initData() {
        leaderCheckRequest ctm = new leaderCheckRequest();
        ctm.userAccountName = MyApplication.userInfo.getAccount();
        // 将数据打包成json字符串
        String param = JsonTools.StringToJson_leaderCharts(ctm);
        activity.getHttp(url, 1, param, new StringCallback() {
            @Override
            public void onAfter(int code) {
                super.onAfter(code);
            }

            @Override
            public void onBefore(Request request, int code) {
                super.onBefore(request, code);
            }

            @Override
            public void onResponse(String response, int code) {
                Log.e(TAG, response);
                dealData(response);
            }

            @Override
            public void onError(Call call, Exception e, int code) {
            }
        });
    }

    private void dealData(String response) {
        if (response == null) {
            //服务器有问题
        }
        //解析数据
        data = (DeviceClassifyInfoResponse) JsonTools.JsonToStruts(response, DeviceClassifyInfoResponse.class);
        if (data.item == null) {
            countPie = 0;
        } else
            countPie = data.item.length;
        putData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    /**
     * 控制正负关系
     *
     * @return
     */
    private int getSign() {
        int[] sign = new int[]{-1, 1};
        return sign[Math.round((float) Math.random())];
    }

    @Override
    protected void onLoad() {
        Log.e(TAG, "onLoad");
    }

    private void putData() {

        tv_userName.setText(MyApplication.userInfo.getUserName());

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
        if (data.item == null) {
            return;
        }
        for (int i = 0; i < data.item.length; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 50) + 50 / 5), data.item[i].typeCn));
        }
        PieDataSet ds1 = new PieDataSet(entries1, "设备类型");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        ds1.setColors(colors);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);
        ds1.setSliceSpace(3f);
        ds1.setSelectionShift(5f);
        ds1.setDrawValues(false);
        //将百分比画在外面
        ds1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieData = new PieData(ds1);
        pieData.setValueTypeface(typeface);
        pieData.setValueFormatter(new PercentFormatter());

        pieChartView.setData(pieData);//设置数据

        myAdapter.notifyDataSetChanged();
        for (int i = 0; i < tags.length; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("name", tags[i]);
            item.put("id", i + "");
            tagList.add(item);
        }
        myAdapter1.notifyDataSetChanged();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pieEntry = (PieEntry) e;
        for (int i = 0; i < tags.length; i++) {
            if (pieEntry.getLabel().equals(tags[i])) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ErrorRecordActivity.class);
                intent.putExtra("id", i + "");
                intent.putExtra("name", tags[i]);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent();
        intent.setClass(getActivity(), ErrorRecordActivity.class);
        intent.putExtra("id", tagList.get(position).get("id"));
        intent.putExtra("name", tags);
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {

        private Context mContext;
        private List<Map<String, String>> list;
        private LayoutInflater mInflater;

        public MyAdapter(Context mContext, List<Map<String, String>> list) {
            this.mContext = mContext;
            this.list = list;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_recyclerview, parent, false);
                mViewHolder.tv_item = (TextView) convertView.findViewById(R.id.recyclerView_item_tv);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.tv_item.setText(list.get(position).get("name"));
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv_item;
    }
}
