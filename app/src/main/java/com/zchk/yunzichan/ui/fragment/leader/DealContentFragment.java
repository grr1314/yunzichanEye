package com.zchk.yunzichan.ui.fragment.leader;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.errorRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/5.
 */
public class DealContentFragment extends Fragment {
    private ListView ls_deal;
    private List<Map<String, String>> list;
    private Map<String, String> item;
    private errorRecord er;
    private MyAdapter myAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.deal_content_fragment, container, false);
        Bundle bundle = getArguments();
        er = (errorRecord) bundle.getSerializable("bean");

        initViews(view);

        initData();
        return view;
    }

    private void initData() {

        if (er.getState() == 1) {
            item = new HashMap<>();
            item.put("name", er.getName());
            item.put("time", er.getTime());
            item.put("state", "已报警");
            item.put("thing", er.getTing());
            item.put("person", "孙予实");
            item.put("stup", "第一步");
            list.add(item);
        } else {
            item = new HashMap<>();
            item.put("name", er.getName());
            item.put("time", er.getTime());
            item.put("state", "已报警");
            item.put("thing", er.getTing());
            item.put("person", "马云飞");
            item.put("stup", "第一步");
            list.add(item);
            item = new HashMap<>();
            item.put("name", er.getName());
            item.put("time", er.getTime());
            item.put("state", "已响应");
//            item.put("thing",er.getTing());
            item.put("person", "李林");
            item.put("stup", "第二步");
            list.add(item);
            item = new HashMap<>();
            item.put("name", er.getName());
            item.put("time", er.getTime());
            item.put("state", "已处理");
//            item.put("thing",er.getTing());
            item.put("person", "李林");
            item.put("stup", "第三步");
            list.add(item);
        }
        myAdapter.notifyDataSetChanged();

    }

    private void initViews(View view) {
        ls_deal = (ListView) view.findViewById(R.id.ls_deal);
        list = new ArrayList<>();
        myAdapter = new MyAdapter();
        ls_deal.setAdapter(myAdapter);
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
            ViewHolder mViewHodler = null;
            if (convertView == null) {
                mViewHodler = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_deal_content, parent, false);
                mViewHodler.name = (TextView) convertView.findViewById(R.id.name);
                mViewHodler.time = (TextView) convertView.findViewById(R.id.time);
                mViewHodler.state = (TextView) convertView.findViewById(R.id.state);
                mViewHodler.person = (TextView) convertView.findViewById(R.id.person);
                mViewHodler.thing = (TextView) convertView.findViewById(R.id.thing);
                mViewHodler.tv_stup = (TextView) convertView.findViewById(R.id.tv_stup);
                mViewHodler.ll_itemBackground= (LinearLayout) convertView.findViewById(R.id.ll_itemBackground);

                convertView.setTag(mViewHodler);
            } else {
                mViewHodler = (ViewHolder) convertView.getTag();
            }
            if (list.get(position).get("state").equals("已处理")) {
                mViewHodler.ll_itemBackground.setBackgroundColor(Color.parseColor("#85B84F"));
            }
            mViewHodler.name.setText("名称：" + list.get(position).get("name"));
            mViewHodler.time.setText("时间：" + list.get(position).get("time"));
            mViewHodler.state.setText("状态：" + list.get(position).get("state"));
            mViewHodler.person.setText("处理人：" + list.get(position).get("person"));
            if (list.get(position).get("thing") == null)
                mViewHodler.thing.setVisibility(View.GONE);
            mViewHodler.thing.setText("异常情况：" + list.get(position).get("thing"));
            mViewHodler.tv_stup.setText(list.get(position).get("stup"));

            return convertView;
        }
    }

    private class ViewHolder {
        TextView name;
        TextView time;
        TextView state;
        TextView person;
        TextView thing;
        TextView tv_stup;
        LinearLayout ll_itemBackground;
    }
}
