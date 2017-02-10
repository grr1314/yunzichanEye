package com.zchk.yunzichan.ui.adapter;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.MipcaActivityCapture;

/**
 * 点检路线适配器
 *
 * @author SenseTech
 */
public class CheckRoundAdapter extends BaseAdapter {

    private Context mContext;
    private List<Map<String, Object>> list;
    private LayoutInflater mInflater;
    private int routeId;
    private adapterClick lis;

    public CheckRoundAdapter(Context mContext, List<Map<String, Object>> list, int routeId) {
        super();
        this.mContext = mContext;
        this.list = list;
        this.routeId = routeId;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_check_round, null);
            mViewHolder.item_tv_deviceName = (TextView) convertView
                    .findViewById(R.id.item_tv_deviceName);
            mViewHolder.item_tv_check = (TextView) convertView
                    .findViewById(R.id.item_tv_check);
            mViewHolder.item_tv_deviceID = (TextView) convertView
                    .findViewById(R.id.item_tv_deviceID);
            mViewHolder.tv_stup = (TextView) convertView
                    .findViewById(R.id.tv_stup);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        @SuppressWarnings("unchecked")
        final Map<String, Object> item = (Map<String, Object>) getItem(position);
        mViewHolder.item_tv_deviceName.setText("设备名称："
                + item.get("deviceName").toString());
        mViewHolder.item_tv_deviceID.setText("设备编号："
                + item.get("labelQr").toString());
        mViewHolder.item_tv_check.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                lis.adapterClick(position);
            }
        });
        return convertView;
    }

    public void setAdapterClick(adapterClick lis) {
        this.lis = lis;
    }

    public interface adapterClick {
        void adapterClick(int pos);
    }

    /**
     * 打开扫一扫界面
     *
     * @param string
     */
    public void openZxing(String string,String taskId) {
        Intent intent = new Intent();
        intent.setClass(mContext, MipcaActivityCapture.class);
        intent.putExtra("ID", string);
        intent.putExtra("taskId", taskId);
        intent.putExtra("ClassNamecn", "HomeSelfCheckActivity");
        mContext.startActivity(intent);
    }

    class ViewHolder {
        TextView item_tv_deviceName;
        TextView item_tv_deviceID;
        TextView item_tv_check;
        TextView tv_stup;
    }

}
