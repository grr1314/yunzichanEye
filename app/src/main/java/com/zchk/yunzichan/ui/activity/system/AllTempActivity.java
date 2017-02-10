package com.zchk.yunzichan.ui.activity.system;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.view.tagView.Tag;
import com.zchk.yunzichan.ui.view.tagView.TagListView;
import com.zchk.yunzichan.ui.view.tagView.TagView;
import com.zchk.yunzichan.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/10.
 */
public class AllTempActivity extends BaseActivity implements TagListView.OnTagLongClickListener,TagListView.OnTagClickListener {
    private TagListView mTagListView;
    private TagListView ls_allTemp;
    private List<Map<String, Object>> list = new ArrayList<>();
    //已经选中的数据
    private List<Map<String, Object>> listChoosed = new ArrayList<>();
    private List<Tag> listChooseedTag = new ArrayList<>();
    private List<Tag> listAllTag=new ArrayList<>();
    private DBUtils dbUtils;

    private final int code = 02;

    private Button btn_complate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alltemp_activity);
        initData();
        initViews();
        initListeners();
    }

    private void initListeners() {
        mTagListView.setOnTagLongClickListener(this);
        ls_allTemp.setOnTagClickListener(this);
        btn_complate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("item", (Serializable) listChooseedTag);
                setResult(code, intent);
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        listChooseedTag = (List<Tag>) intent.getSerializableExtra("listChoosed");

    }


    private void initViews() {
        dbUtils = new DBUtils(initApplication());
        ls_allTemp = (TagListView) findViewById(R.id.ls_allTemp);

        mTagListView = (TagListView) findViewById(R.id.tagview);
        //设置字体颜色
        mTagListView.setTagViewTextColorRes(Color.parseColor("#ffffff"));
        mTagListView.setTags(listChooseedTag);

        //从本地获取数据
        list = dbUtils.selectAssetType();
        for (int i=0;i<list.size();i++)
        {
            Tag tag = new Tag();
            tag.setTitle(list.get(i).get("name").toString());
            tag.setTempCode(list.get(i).get("typeCode").toString());
            listAllTag.add(tag);
        }

        btn_complate= (Button) findViewById(R.id.btn_complate);

        //设置字体颜色
        ls_allTemp.setTagViewTextColorRes(Color.parseColor("#638296"));
        //设置背景
        ls_allTemp.setTagViewBackgroundRes(R.drawable.bg_tagview_all);
        ls_allTemp.setTags(listAllTag);

        initTopBar("类型定制", true, false, true);
        initTopBarListener(null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("item", (Serializable) listChooseedTag);
                setResult(code, intent);
                finish();
            }
        });


    }

    @Override
    public void onTagLongClick(TagView tagView, Tag tag) {
        mTagListView.setDeleteMode(tag,true);
        ToastUtil.showToast(this,tag.getTitle().toString());
    }

    @Override
    public void onTagClick(TagView tagView, Tag tag) {
        addTag(tag);
    }

    /**
     * 添加Tag
     * @param tag
     */
    private void addTag(Tag tag) {
        listChooseedTag.add(tag);
        notifiTagList();
    }

    /**
     * 刷新已有类型
     */
    private void notifiTagList() {
        mTagListView.setTags(listChooseedTag);
    }
}