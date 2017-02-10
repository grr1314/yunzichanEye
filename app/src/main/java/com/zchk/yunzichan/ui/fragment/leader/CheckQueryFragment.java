package com.zchk.yunzichan.ui.fragment.leader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.IconPagerAdapter;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.Device;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.system.AllTempActivity;
import com.zchk.yunzichan.ui.fragment.base.BaseFragment;
import com.zchk.yunzichan.ui.view.tagView.Tag;
import com.zchk.yunzichan.utils.Constant;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 领导督察 点检页面
 *
 * @author SenseTech
 */
public class CheckQueryFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "CheckQueryFragment";

    private TabLayout tabLayout;

    private ImageView btn_add;
    private ViewPager vp_leaderCheck;
    private LinearLayout ll_add;
    private List<Tag> list = new ArrayList<>();

    private ViewPagerAdapter mAdapter;
    List<Device> p2 = new ArrayList<Device>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.check_maintenance_layoutone,
                container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        btn_add.setOnClickListener(this);
    }

    private void initView(View view) {

        Bundle bundle = getArguments();
        int id = bundle.getInt("id");

        btn_add = (ImageView) view.findViewById(R.id.btn_add);
        ll_add= (LinearLayout) view.findViewById(R.id.ll_add);
        if (id == Constant.realTimeDataActivity) {
            ll_add.setVisibility(View.GONE);

        }
        vp_leaderCheck = (ViewPager) view.findViewById(R.id.vp_leaderCheck);
        vp_leaderCheck.setPageMargin((int) getResources().getDimensionPixelOffset(R.dimen.ui_pager_space));

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonListTest = share.getDeviceInfo(getActivity());
        Log.e(TAG, jsonListTest);

        Type type1 = new TypeToken<List<Device>>() {
        }.getType();
        p2 = gson.fromJson(jsonListTest, type1);

        for (int i = 0; i < p2.size(); i++) {
            Tag tag = new Tag();
            tag.setTitle(p2.get(i).getNameCn());
            tag.setTempCode(p2.get(i).getTypeName());
            tabLayout.addTab(tabLayout.newTab().setText(p2.get(i).getNameCn()));
            list.add(tag);
        }
        addTalListener();
        vp_leaderCheck.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mAdapter = new ViewPagerAdapter(getFragmentManager());
        vp_leaderCheck.setAdapter(mAdapter);

    }

    private void Intent(Class cls) {
        android.content.Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        //传递当前已经选中的设备
        intent.putExtra("listChoosed", (Serializable) list);
        this.startActivityForResult(intent, 02);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add: {
                Intent(AllTempActivity.class);
            }
            break;
            default:
                break;
        }
    }

    private void addTalListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                vp_leaderCheck.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onLoad() {

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter implements
            IconPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new DeviceCheckFragment();
            Bundle args = new Bundle();
            args.putString("titles", list.get(position).getTitle());
            args.putString("tempCode", list.get(position).getTempCode());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public int getIconResId(int index) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return list.get(position).getTitle();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult");
        if (resultCode == 02) {
            List<Tag> listBack = (List<Tag>) data.getSerializableExtra("item");
            list.clear();
            tabLayout.removeAllTabs();
            for (int i = 0; i < listBack.size(); i++) {
                list.add(listBack.get(i));
                tabLayout.addTab(tabLayout.newTab().setText(listBack.get(i).getTitle()));
            }
            mAdapter.notifyDataSetChanged();
            vp_leaderCheck.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }
}