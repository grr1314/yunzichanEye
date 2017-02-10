package com.zchk.yunzichan.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.zchk.yunzichan.utils.WidthHeight;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by shichaohui on 2015/7/10 0010.
 * <p/>
 * ҳ��ָʾ���࣬��ô���ʵ���󣬿�ͨ��{@link PageIndicatorView#initIndicator(int)}������ʼ��ָʾ��
 * </P>
 */
public class PageIndicatorView extends LinearLayout {

    private Context mContext = null;
    private int dotSize = 7; // ָʾ���Ĵ�С��dp��
    private int margins = 4; // ָʾ����ࣨdp��
    private List<View> indicatorViews = null; // ���ָʾ��

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi") public PageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        dotSize = WidthHeight.dip2px(context, dotSize);
        margins = WidthHeight.dip2px(context, margins);
    }

    /**
     * ��ʼ��ָʾ����Ĭ��ѡ�е�һҳ
     *
     * @param count ָʾ����������ҳ��
     */
    public void initIndicator(int count) {

        if (indicatorViews == null) {
            indicatorViews = new ArrayList<View>();
        } else {
            indicatorViews.clear();
            removeAllViews();
        }
        if (count<=1)
        {
            setVisibility(View.GONE);
            return;
        }
        View view;
        LayoutParams params = new LayoutParams(dotSize, dotSize);
        params.setMargins(margins, margins, margins, margins);
        for (int i = 0; i < count; i++) {
            view = new View(mContext);
            view.setBackgroundResource(android.R.drawable.presence_invisible);
            addView(view, params);
            indicatorViews.add(view);
        }
        if (indicatorViews.size() > 0) {
            indicatorViews.get(0).setBackgroundResource(android.R.drawable.presence_online);
        }
    }

    /**
     * ����ѡ��ҳ
     *
     * @param selected ҳ�±꣬��0��ʼ
     */
    public void setSelectedPage(int selected) {
        for (int i = 0; i < indicatorViews.size(); i++) {
            if (i == selected) {
                indicatorViews.get(i).setBackgroundResource(android.R.drawable.presence_online);
            } else {
                indicatorViews.get(i).setBackgroundResource(android.R.drawable.presence_invisible);
            }
        }
    }
}

