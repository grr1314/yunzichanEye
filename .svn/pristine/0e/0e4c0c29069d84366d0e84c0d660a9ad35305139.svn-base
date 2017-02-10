package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.check.CheckMaintenanceSearchTwoManager;
import com.zchk.yunzichan.ui.activity.file.RelevantFileActivity;

public class Check_Maintenance_AdapterTwo extends BaseAdapter {

    private List<CheckMaintenanceSearchTwoManager> mData;
    private LayoutInflater mInflater;

    private Context context;

    public Check_Maintenance_AdapterTwo(Context context,
                                        List<CheckMaintenanceSearchTwoManager> data) {

        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.check_maintenance_list_layouttwo, null);
            holder = new ViewHolder();
            holder.check_deviceName_list = (TextView) convertView
                    .findViewById(R.id.maintenance_devicename_two);
            holder.check_time_list = (TextView) convertView
                    .findViewById(R.id.check_maintenance_time_two);
            holder.check_noet_list = (TextView) convertView
                    .findViewById(R.id.check_maintenance_note_two);
            holder.check_account = (TextView) convertView
                    .findViewById(R.id.check_maintenance_account_two);
            holder.check_file = (TextView) convertView
                    .findViewById(R.id.check_maintenance_text_two);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String NameCn = "设备名称:" + mData.get(position).deviceID;
        holder.check_deviceName_list.setText(NameCn);
        holder.check_time_list.setText("时间：" + mData.get(position).time);
        holder.check_noet_list.setText("记录：" + mData.get(position).note);
        String ckeckNameCn = "维保人:" + mData.get(position).account;
        holder.check_account.setText(ckeckNameCn);

        // 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
        holder.check_account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mInflater.getContext())
                        .setTitle(mData.get(position).account + "维保详细信息")
                        .setMessage(
                                "设备名称 : " + mData.get(position).deviceID + "\n"
                                        + "维保时间 : " + mData.get(position).time
                                        + "\n" + "维保结果 : "
                                        + mData.get(position).note).show();
            }
        });

        holder.check_file.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, RelevantFileActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public static class ViewHolder {

        public TextView check_deviceName_list;
        public TextView check_time_list;
        public TextView check_noet_list;
        public TextView check_account;
        public TextView check_file;
    }

}

