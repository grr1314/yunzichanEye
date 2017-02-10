package com.zchk.yunzichan.ui.fragment.leader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.errorRecord;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.leader.DealActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/5.
 */
public class ErrorContentFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "ErrorContentFragment";
    private ListView ls_error_content;
    private List<errorRecord> list;
    private MyAdapter myAdapter;

    private TextView ls_empty;
    private String id;
    private String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.leader_error_content, container, false);
        initData();
        initViews(view);

        ls_error_content.setOnItemClickListener(this);
        return view;
    }
    private void initData() {
        list = new ArrayList<>();
        errorRecord error = new errorRecord();
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        Log.e(TAG, "id:" + id);
        name = bundle.getString("name");

        switch (Integer.valueOf(id)) {
            case 0: {
                error.setId(1);
                error.setName("十二楼楼顶西侧");
                error.setState(0);
                error.setTime("2016/11/2 ");
                error.setType("巡更点");
                error.setTing("门窗轻微破损");
                list.add(error);
                error = new errorRecord();
                error.setId(1);
                error.setName("十一楼西侧");
                error.setState(1);
                error.setTime("2016/11/2 ");
                error.setTing("杂物堆积");
                error.setType("巡更点");
                list.add(error);
                error = new errorRecord();
                error.setId(1);
                error.setName("十楼东侧");
                error.setState(0);
                error.setTime("2016/11/3 ");
                error.setType("巡更点");
                error.setTing("告示掉落");
                list.add(error);
            }
            break;
            case 1: {

            }
            break;
            case 2: {
                error.setId(1);
                error.setName("三号新风机组");
                error.setState(0);
                error.setTime("2016/11/8");
                error.setType("新风机组");
                error.setTing("压力值过高");
                list.add(error);
                error = new errorRecord();
                error.setId(1);
                error.setName("六号新风机组");
                error.setState(0);
                error.setTime("2016/10/18");
                error.setType("新风机组");
                error.setTing("有异味");
                list.add(error);
                if (share.getError(getActivity()))
                {
                    error = new errorRecord();
                    error.setId(1);
                    error.setName(share.getErrorName(getActivity()));
                    error.setState(1);
                    error.setTime("2017/1/26");
                    error.setType("新风机组");
                    error.setTing("有异常");
                    list.add(error);
                }
            }
            break;
            case 3: {
                error.setId(1);
                error.setName("二号层间电气井");
                error.setState(0);
                error.setTime("2016/10/22");
                error.setType("层间电气井");
                error.setTing("室温略高");
                list.add(error);
            }
            break;
            case 4: {

            }
            break;
            case 5: {
                error.setId(1);
                error.setName("一号新型电梯机房");
                error.setState(0);
                error.setTime("2016/11/25  14:25:00");
                error.setType("电梯机房");
                error.setTing("门窗未关闭");
                list.add(error);
            }
            break;
            case 6: {
                error = new errorRecord();
                error.setId(1);
                error.setName("1号电梯内");
                error.setState(0);
                error.setTime(" 2016/11/28 15:205");
                error.setType("监控摄像机");
                error.setTing("灰尘未清理");
                list.add(error);
                error = new errorRecord();
                error.setId(1);
                error.setName("6F走廊东侧");
                error.setState(0);
                error.setTime("2016/11/28 15:35");
                error.setType("监控摄像机");
                error.setTing("电源线脱落");
                list.add(error);
                error = new errorRecord();
                error.setId(1);
                error.setName("2F扶梯口");
                error.setState(0);
                error.setTime("2016/11/28 16:21");
                error.setType("监控摄像机");
                error.setTing("视频接口脱落");
                list.add(error);
                error = new errorRecord();
                error.setId(1);
                error.setName(" 小二楼对面");
                error.setState(0);
                error.setTime("2016/11/28  17:25:00 ");
                error.setType("监控摄像机");
                error.setTing("电源接触不良");
                list.add(error);
            }
            break;
            default:
                break;
        }
    }

    private void initViews(View view) {
        ls_error_content = (ListView) view.findViewById(R.id.ls_error_content);
        ls_empty = (TextView) view.findViewById(R.id.ls_empty);
        if (list.size()==0)
        {
            ls_error_content.setEmptyView(ls_empty);
        }
        myAdapter = new MyAdapter();
        ls_error_content.setAdapter(myAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        errorRecord er = list.get(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), DealActivity.class);
        intent.putExtra("bean", (Serializable) er);
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {

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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_error_record, parent, false);
                mViewHolder.tv_item_errorName = (TextView) convertView.findViewById(R.id.tv_item_errorName);
                mViewHolder.tv_item_errorState = (TextView) convertView.findViewById(R.id.tv_item_errorState);
                mViewHolder.ll_itemBackground= (LinearLayout) convertView.findViewById(R.id.ll_itemBackground);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.tv_item_errorName.setText("设备名称："+list.get(position).getName());
            if (list.get(position).getState() == 0)
            {
                mViewHolder.ll_itemBackground.setBackgroundColor(Color.parseColor("#88BA53"));
            }

            mViewHolder.tv_item_errorState.setText("状态："+(list.get(position).getState() == 0 ? "已处理" : "未处理"));
            return convertView;
        }
    }

    private class ViewHolder {

        TextView tv_item_errorName;
        TextView tv_item_errorState;
        LinearLayout ll_itemBackground;
    }
}
