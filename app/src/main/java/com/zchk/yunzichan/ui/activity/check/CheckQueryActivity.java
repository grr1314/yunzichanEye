package com.zchk.yunzichan.ui.activity.check;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.entity.bean.Device;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.system.AllTempActivity;
import com.zchk.yunzichan.ui.fragment.check.Check_Fragment;
import com.zchk.yunzichan.ui.view.tagView.Tag;
import com.zhy.http.okhttp.callback.Callback;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 批量查询检点信息
 *
 * @author SenseTech
 */
public class CheckQueryActivity extends BaseActivity implements
        View.OnClickListener {

    private static final int SEARCH = 0;
    private static final String TAG = "CheckQueryActivity";

    private ViewPager viewpager;

    private FragmentPagerAdapter vPadapter;
    private Button btn_add;

    private String[] titles;
    private String[] temps;

    private TabLayout tabLayout;

    private List<Tag> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_query);
        initView();
        initListener();
        initFragment();
        initTopBarListener(null, null, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {

    }

    @SuppressWarnings("deprecation")
    private void initFragment() {
        vPadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position).getTitle();
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new Check_Fragment();
                Bundle args = new Bundle();
                args.putString("tempCode", list.get(position).getTempCode());
                fragment.setArguments(args);
                return fragment;
            }
        };

        viewpager.setAdapter(vPadapter);
    }

    private void initView() {
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        newTabel();
        addTalListener();
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        initTopBar("点检记录查询", true, false, true);
    }

    private void addTalListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void newTabel() {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonListTest= share.getDeviceInfo(CheckQueryActivity.this);
        List<Device> p2=new ArrayList<Device>();
        Type type1=new TypeToken<List<Device>>(){}.getType();
        p2=gson.fromJson(jsonListTest, type1);

        for (int i = 0; i < p2.size(); i++) {
            Tag tag = new Tag();
            tag.setTitle(p2.get(i).getNameCn());
            tag.setTempCode(p2.get(i).getTypeName());
            tabLayout.addTab(tabLayout.newTab().setText(p2.get(i).getNameCn()));
            list.add(tag);
        }
    }

    public MyApplication getMyApplication() {
        return initApplication();
    }

    private void Intent(Class cls) {
        Intent intent = new Intent();
        intent.setClass(CheckQueryActivity.this, cls);
        //传递当前已经选中的设备
        intent.putExtra("listChoosed", (Serializable) list);
        startActivityForResult(intent, 02);
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

    /**
     * 获取http方法
     */
    public String getHttp(String str, int type, String param,
                          Callback<String> call) {
        return http(str, type, param, call);
    }

    public int getNetMode()
    {
        return checkMode();
    }
}
