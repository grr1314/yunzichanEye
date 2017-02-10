package com.zchk.yunzichan.ui.fragment.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.model.check.CheckRecordDetailsItem;
import com.zchk.yunzichan.ui.view.NoScrollListView;
import com.zchk.yunzichan.utils.JsonTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/1/12.
 */
public class DetailLeaderCheckFrgamet extends Fragment {
    private String result;

    private TextView tv_deviceId;
    private TextView tv_deviceName;
    private TextView tv_deviceType;
    private TextView tv_deviceS;
    private TextView tv_deviceTime;
    private TextView tv_peopleCheck;
    private TextView tv_checkContent;

    private NoScrollListView ls_allTemp;

    MyAdapter adapter;

    List<Map<String, String>> list = new ArrayList<>();
    Map<String, String> item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.check_detail_activity, container, false);
        Bundle bundle = getArguments();
        result = bundle.getString("info");

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        tv_deviceId = (TextView) view.findViewById(R.id.tv_deviceId);
        tv_deviceName = (TextView) view.findViewById(R.id.tv_deviceName);
        tv_deviceType = (TextView) view.findViewById(R.id.tv_deviceType);
        tv_deviceS = (TextView) view.findViewById(R.id.tv_deviceS);
        tv_deviceTime = (TextView) view.findViewById(R.id.tv_deviceTime);
        tv_peopleCheck = (TextView) view.findViewById(R.id.tv_peopleCheck);
        ls_allTemp = (NoScrollListView) view.findViewById(R.id.ls_allTemp);

        adapter = new MyAdapter();
        putData();
    }

    private void putData() {
        CheckRecordDetailsItem items = (CheckRecordDetailsItem) JsonTools.JsonToStruts(result, CheckRecordDetailsItem.class);
        tv_deviceId.setText("设备编号：" + items.qrCode);
        tv_deviceName.setText("设备名称：" + items.nameCn);
        tv_deviceType.setText("设备类型：" + items.typeCn);
        tv_deviceS.setText("供应商：" + items.companyCn);
        tv_deviceTime.setText("点检时间：" + items.checkTime);
        tv_peopleCheck.setText("点检人：" + items.checkUser);
        for (int i = 0; i < items.checkNote.length; i++) {
            item = new HashMap<>();
            item.put("nameCn", items.checkNote[i].nameCn);
            item.put("value", items.checkNote[i].value);
            list.add(item);
        }
        ls_allTemp.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_check_detail, null);
                mViewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_item_detail);
                mViewHolder.tv_item_Type = (TextView) convertView.findViewById(R.id.tv_item_Type);
                convertView.setTag(mViewHolder);
            } else
                mViewHolder = (ViewHolder) convertView.getTag();
            mViewHolder.tv_item.setText(list.get(position).get("value"));
            mViewHolder.tv_item_Type.setText(list.get(position).get("nameCn"));
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv_item;
        TextView tv_item_Type;

    }
}
