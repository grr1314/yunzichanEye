/**
 *
 */
package com.zchk.yunzichan.ui.view.tagView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.CompoundButton;

import com.zchk.yunzichan.R;

public class TagListView extends FlowLayout implements OnClickListener, OnLongClickListener {

    private static final String TAG = "TagListView";
    private boolean hasNewTextColor = false;
    private boolean hasNewBackgroundRes = false;
    private boolean mIsDeleteMode;
    private OnTagCheckedChangedListener mOnTagCheckedChangedListener;
    private OnTagClickListener mOnTagClickListener;
    private OnTagLongClickListener mOnTagLongClickListener;
    private int mTagViewBackgroundResId;
    private int mTagViewTextColorResId;
    private final List<Tag> mTags = new ArrayList<>();

    private int defaultTextColor;
    private int defaultBackgroundRes;

    /**
     * @param context
     */
    public TagListView(Context context) {
        super(context);
        init();
    }
    /**
     * @param context
     * @param attributeSet
     */
    public TagListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    /**
     * @param context
     * @param attributeSet
     * @param defStyle
     */
    public TagListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }
    private void init() {
        defaultTextColor = getResources().getColor(R.color.blue);
        defaultBackgroundRes=R.drawable.tag_bg;
    }

    @Override
    public void onClick(View v) {
        if ((v instanceof TagView)) {
            Tag localTag = (Tag) v.getTag();
            if (this.mOnTagClickListener != null) {
                this.mOnTagClickListener.onTagClick((TagView) v, localTag);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if ((v instanceof TagView)) {
            Tag localTag = (Tag) v.getTag();
            if (this.mOnTagLongClickListener != null) {
                this.mOnTagLongClickListener.onTagLongClick((TagView) v, localTag);
            }
        }
        return true;
    }
    /**
     *
     */
    public void updateTagView(final Tag t)
    {
        Log.e(TAG,"updateTagView");
        TagView localTagView = (TagView) View.inflate(getContext(),
                R.layout.tag, null);
        if (mIsDeleteMode) {
            int k = (int) TypedValue.applyDimension(1, 5.0F, getContext()
                    .getResources().getDisplayMetrics());
            localTagView.setPadding(localTagView.getPaddingLeft(),
                    localTagView.getPaddingTop(), k,
                    localTagView.getPaddingBottom());

            //添加图片
            localTagView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.forum_tag_close, 0);

        }
    }

    /**
     * @param t
     * @param b
     */
    private void inflateTagView(final Tag t, boolean b) {

        TagView localTagView = (TagView) View.inflate(getContext(),
                R.layout.tag, null);
        localTagView.setText(t.getTitle());
        localTagView.setTag(t);
        if (hasNewTextColor) {
            localTagView.setTextColor(mTagViewTextColorResId);
        }
        else
            localTagView.setTextColor(defaultTextColor);
        if (hasNewBackgroundRes) {
            localTagView.setBackgroundResource(mTagViewBackgroundResId);
        }
        else
            localTagView.setBackgroundResource(defaultBackgroundRes);

        localTagView.setChecked(t.isChecked());
        localTagView.setCheckEnable(b);
        if (mIsDeleteMode) {
            int k = (int) TypedValue.applyDimension(1, 5.0F, getContext()
                    .getResources().getDisplayMetrics());
            localTagView.setPadding(localTagView.getPaddingLeft(),
                    localTagView.getPaddingTop(), k,
                    localTagView.getPaddingBottom());

            //添加图片
            localTagView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.forum_tag_close, 0);

        }
        if (t.getBackgroundResId() > 0) {
            localTagView.setBackgroundResource(t.getBackgroundResId());
        }
        if ((t.getLeftDrawableResId() > 0) || (t.getRightDrawableResId() > 0)) {
            localTagView.setCompoundDrawablesWithIntrinsicBounds(
                    t.getLeftDrawableResId(), 0, t.getRightDrawableResId(), 0);
        }
        localTagView.setOnClickListener(this);
        localTagView.setOnLongClickListener(this);
        localTagView
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(
                            CompoundButton paramAnonymousCompoundButton,
                            boolean paramAnonymousBoolean) {
                        t.setChecked(paramAnonymousBoolean);
                        if (TagListView.this.mOnTagCheckedChangedListener != null) {
                            TagListView.this.mOnTagCheckedChangedListener
                                    .onTagCheckedChanged(
                                            (TagView) paramAnonymousCompoundButton,
                                            t);
                        }
                    }
                });
        addView(localTagView);
    }

    public void addTag(int i, String s) {
        addTag(i, s, false);
    }

    public void addTag(int i, String s, boolean b) {
        addTag(new Tag(i, s), b);
    }

    public void addTag(Tag tag) {
        addTag(tag, false);
    }

    public void addTag(Tag tag, boolean b) {
        mTags.add(tag);
        inflateTagView(tag, b);
    }

    public void addTags(List<Tag> lists) {
        addTags(lists, false);
    }

    public void addTags(List<Tag> lists, boolean b) {
        for (int i = 0; i < lists.size(); i++) {
            addTag((Tag) lists.get(i), b);
        }
    }

    public List<Tag> getTags() {
        return mTags;
    }

    public View getViewByTag(Tag tag) {
        return findViewWithTag(tag);
    }

    public void removeTag(Tag tag) {
        mTags.remove(tag);
        removeView(getViewByTag(tag));
    }

    /**
     * 设置为删除模式
     * @param tag
     * @param b
     */
    public void setDeleteMode(Tag tag,boolean b) {
        mIsDeleteMode = b;
        updateTagView(tag);
    }

    public void setOnTagCheckedChangedListener(
            OnTagCheckedChangedListener onTagCheckedChangedListener) {
        mOnTagCheckedChangedListener = onTagCheckedChangedListener;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public void setOnTagLongClickListener(OnTagLongClickListener onTagLongClickListener) {
        mOnTagLongClickListener = onTagLongClickListener;
    }

    public void setTagViewBackgroundRes(int res) {
        if (!hasNewBackgroundRes)
        {
            mTagViewBackgroundResId = res;
            hasNewBackgroundRes=true;

        }

    }

    public void setTagViewTextColorRes(int res) {
        if (!hasNewTextColor)
        {
            hasNewTextColor=true;
            mTagViewTextColorResId = res;
        }

    }

    public void setTags(List<? extends Tag> lists) {
        setTags(lists, false);
    }

    public void setTags(List<? extends Tag> lists, boolean b) {
        removeAllViews();
        mTags.clear();
        for (int i = 0; i < lists.size(); i++) {
            addTag((Tag) lists.get(i), b);
        }
    }


    /**
     * 接口
     */
    public static abstract interface OnTagCheckedChangedListener {
        public abstract void onTagCheckedChanged(TagView tagView, Tag tag);
    }

    public static abstract interface OnTagClickListener {
        public abstract void onTagClick(TagView tagView, Tag tag);
    }

    public static abstract interface OnTagLongClickListener {
        public abstract void onTagLongClick(TagView tagView, Tag tag);
    }

}
