package com.zchk.yunzichan.ui.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * create by admin 2016/10/11
 */
public class AutoGridLayoutManager extends GridLayoutManager {

    private static final String TAG = "AutoGridLayoutManager";
    private int measuredWidth = 0;
    private int measuredHeight = 0;
    private int indicatorHeight = 0;
    private int spanCount;
    private int cowCount;
    public AutoGridLayoutManager(Context context, AttributeSet attrs,
                                 int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AutoGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        this.spanCount=spanCount;
    }

    public AutoGridLayoutManager(Context context, int spanCount,
                                 int orientation, boolean reverseLayout, int indicatorHeight) {
        super(context, spanCount, orientation, reverseLayout);
        this.indicatorHeight = indicatorHeight;


        init();
    }

    private void init() {

    }


    @Override
    public void onMeasure(RecyclerView.Recycler recycler,
                          RecyclerView.State state, int widthSpec, int heightSpec) {
        Log.e(TAG, "Frist IndicatorHeight:" + indicatorHeight);

        if (measuredHeight <= 0) {
            //获取一个Item的view，注意如果一开始没有数据则就没有Item的view，所以这里会报错
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                measureChild(view, widthSpec, heightSpec);
                measuredWidth = View.MeasureSpec.getSize(widthSpec);
                measuredHeight = view.getMeasuredHeight() * getSpanCount();
                Log.e(" view.getMeasuredHeight", view.getMeasuredHeight()+" "+spanCount);
//                cowCount
            }
        }
//        setMeasuredDimension(measuredWidth, measuredWidth - indicatorHeight);
        Log.e("measuredHeight",measuredHeight+""+spanCount);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }



}