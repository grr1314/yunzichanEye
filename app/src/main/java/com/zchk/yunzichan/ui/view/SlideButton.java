package com.zchk.yunzichan.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zchk.yunzichan.R;

/**
 * 问题：本例中所有用到的图片均是由固定大小，这样是否不利于view本身的大小设置
 * Created by HP on 2016/6/20.
 */
public class SlideButton extends View {
    private static final String TAG = "SlideButton";
    //控件的三个状态
    private static final int STATUS_OFF = 0;//关闭状态
    private static final int STATUS_ON = 1;//打开状态
    private static final int SCROLLING = 2;//正在滑动

    private boolean hasScrolled;

    private OnSwitchChangedListener mOnSwitchChangedListener = null;
    //用于显示的文字
    private static final String txtOn = "打开";
    private static final String txtOff = "关闭";
    //控件的状态
    private int state;

    //画笔
    Paint paint = new Paint();
    //获取三张图片
    Bitmap statusOn;//打开时的图片
    Bitmap statusOff;//关闭时的图片
    Bitmap thums;//中间的圆图

    private int mSrcX = 0;
    private int mDstX = 0;


    float textWidth;
    float textHeight;

    //表示中间圆图的宽度
    int thumWidth;
    //表示SwitchView的宽度
    int bitmapWidth;
    int bitmapHeight;

    public SlideButton(Context context) {
        this(context, null);
    }

    public SlideButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //默认的状态时关闭
        state = STATUS_OFF;
//        state = STATUS_ON;
        //获取打开时的背景图片
        statusOn = BitmapFactory.decodeResource(getResources(), R.drawable.bg_switch_on);
        //获取关闭时的背景图片
        statusOff = BitmapFactory.decodeResource(getResources(), R.drawable.bg_switch_off);
        //获取圆图
        thums = BitmapFactory.decodeResource(getResources(), R.drawable.switch_thumb);

        //获取图片的宽度
        thumWidth = thums.getWidth();
        bitmapWidth = statusOn.getWidth();
        bitmapHeight = statusOn.getHeight();

        //设置画笔颜色
        paint.setColor(Color.BLACK);
        paint.setTextSize(18);

    }

    /**
     * 设置按钮的状态
     *
     * @param status
     */
    public void setStatus(boolean status) {
        if (status) {
            state = STATUS_ON;
        } else
            state = STATUS_OFF;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                Log.e(TAG, "down");
                mSrcX = (int) event.getX();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                Log.e(TAG, "move");
                mDstX = Math.max((int) event.getX(), thumWidth / 2);
                mDstX = Math.min(mDstX, bitmapWidth - thumWidth / 2);
                if (mSrcX == mDstX)
                    return true;
                hasScrolled = true;
                AnimationTransRunnable aTransRunnable = new AnimationTransRunnable(mSrcX, mDstX, 0);
                new Thread(aTransRunnable).start();
                mSrcX = mDstX;

            }
            break;
            case MotionEvent.ACTION_UP: {
                Log.e(TAG, "up");
                if (hasScrolled == false)//如果没有发生过滑动，就意味着这是一次单击过程
                {
                    state = Math.abs(state - 1);
                    int xFrom = 10, xTo = 62;
                    if (state == STATUS_OFF) {
                        xFrom = 62;
                        xTo = 10;
                    }
                    AnimationTransRunnable runnable = new AnimationTransRunnable(xFrom, xTo, 1);
                    new Thread(runnable).start();
                } else {
                    invalidate();
                    hasScrolled = false;
                }
                //状态改变的时候 回调事件函数
                if (mOnSwitchChangedListener != null) {
                    mOnSwitchChangedListener.onSwitchChanged(this, state);
                }
            }
            break;
        }
        return true;
    }

    /**
     * 为开关控件设置状态改变监听函数
     *
     * @param onSwitchChangedListener 参见 {@link OnSwitchChangedListener}
     */
    public void setOnSwitchChangedListener(OnSwitchChangedListener onSwitchChangedListener) {
        mOnSwitchChangedListener = onSwitchChangedListener;
    }

    public static interface OnSwitchChangedListener {
        /**
         * 状态改变 回调函数
         *
         * @param status SWITCH_ON表示打开 SWITCH_OFF表示关闭
         */
        public abstract void onSwitchChanged(SlideButton obj, int status);
    }

    /**
     * 画图片
     */
    public void drawBitmap(Canvas canvas, Rect src, Rect dst, Bitmap bitmap) {
        dst = (dst == null ? new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()) : dst);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, src, dst, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (getMeasuredWidth() > bitmapWidth) {
            width = bitmapWidth;
        }
        if (getMeasuredHeight() > thums.getHeight()) {
            height = thums.getHeight();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //当状态处于打开状态时
        if (state == STATUS_ON) {

            //第一步：画出背景图片
            drawBitmap(canvas, null, null, statusOn);
            //相见之前所画的部分保存
            int count = canvas.save();
            //因为当处于打开状态时，圆形图片在右边
            canvas.translate(statusOn.getWidth() - thums.getWidth(), (statusOn.getHeight() - thums.getHeight()) / 2);
            //第二步：画出中间的圆形图
            drawBitmap(canvas, null, null, thums);
            //第三步：画出文字（注意要画在圆形图片之后）
            canvas.restoreToCount(count);
            paint.setColor(Color.WHITE);
            //注意x y表示起点的坐标，注意绘制文字的起点是在文字的左下角
            getTextWidthAndHeight(txtOn);
            int startX = (int) ((bitmapWidth - thumWidth) - textWidth);
            Log.e(TAG, "bitmapHeight:" + bitmapWidth + "----thumWidth:" + thumWidth);
            Log.e(TAG, "X:" + startX + "----textWidth" + textWidth);
            int startY = (int) (bitmapHeight / 2 + textHeight / 2);
            Log.e(TAG, "startY:" + startY + "textHeight:" + textHeight);
            canvas.drawText(txtOn, startX, startY - 5, paint);

        }
        //当状态处于关闭时
        else if (state == STATUS_OFF) {
            //第一步：画出背景图片
            drawBitmap(canvas, null, null, statusOff);
            canvas.translate(0, (statusOn.getHeight() - thums.getHeight()) / 2);
//            canvas.save();
            //第二步：画出中间的圆形图
            drawBitmap(canvas, null, null, thums);
            //第三步：画出文字（注意要画在圆形图片之后）
            canvas.translate(thums.getWidth(), 0);
            paint.setColor(Color.BLACK);
            getTextWidthAndHeight(txtOff);
            int startX = (int) ((bitmapWidth - thumWidth) - textWidth);
            int startY = (int) (bitmapHeight / 2 + textHeight / 2);
            canvas.drawText(txtOff, 0, startY, paint);

        }
        //当状态处于滑动时
        else {
            //根据手指滑动的距离来判断当前处于开还是关闭的状态
            //思路就是不停的画矩形
            state = mDstX > bitmapWidth / 2 ? STATUS_ON : STATUS_OFF;
            //
            drawBitmap(canvas, new Rect(0, 0, mDstX, statusOff.getHeight()), new Rect(0, 0, mDstX, statusOff.getHeight()), statusOn);
            paint.setColor(Color.WHITE);
            getTextWidthAndHeight(txtOn);
            int startX = (int) ((bitmapWidth - thumWidth) - textWidth);
            int startY = (int) (bitmapHeight / 2 + textHeight / 2);
            //注意与打开状态时的位置保持一致
            canvas.drawText(txtOn, startX, startY, paint);

            int count = canvas.save();
            canvas.translate(mDstX, 0);
            //这句话控制着关闭时的背景图片不动
            drawBitmap(canvas, new Rect(mDstX, 0, bitmapWidth, statusOff.getHeight()), new Rect(0, 0, bitmapWidth - mDstX, statusOff.getHeight()), statusOff);

            canvas.restoreToCount(count);
            count = canvas.save();

            canvas.clipRect(mDstX, 0, bitmapWidth, statusOff.getHeight());
            canvas.translate(thumWidth, (statusOn.getHeight() - thums.getHeight()) / 2);

            paint.setColor(Color.rgb(105, 105, 105));
            //注意与关闭时的位置保持一致
            getTextWidthAndHeight(txtOff);
            startX = (int) ((bitmapWidth - thumWidth) - textWidth);
            startY = (int) (bitmapHeight / 2 + textHeight / 2);
            canvas.drawText(txtOff, 0, startY - 10, paint);
            canvas.restoreToCount(count);

            count = canvas.save();

            canvas.translate(mDstX - thumWidth / 2, (statusOn.getHeight() - thums.getHeight()) / 2);
            drawBitmap(canvas, null, null, thums);
            canvas.restoreToCount(count);

        }

    }

    /**
     * AnimationTransRunnable 做滑动动画所使用的线程
     */
    private class AnimationTransRunnable implements Runnable {
        private int srcX, dstX;
        private int duration;

        /**
         * 滑动动画
         *
         * @param srcX     滑动起始点
         * @param dstX     滑动终止点
         * @param duration 是否采用动画，1采用，0不采用
         */
        public AnimationTransRunnable(float srcX, float dstX, final int duration) {
            this.srcX = (int) srcX;
            this.dstX = (int) dstX;
            this.duration = duration;
        }

        @Override
        public void run() {
            final int patch = (dstX > srcX ? 5 : -5);
            if (duration == 0) {
                SlideButton.this.state = SCROLLING;
                SlideButton.this.postInvalidate();
            } else {
                Log.d(TAG, "start Animation: [ " + srcX + " , " + dstX + " ]");
                int x = srcX + patch;
                while (Math.abs(x - dstX) > 5) {
                    mDstX = x;
                    SlideButton.this.state = SCROLLING;
                    SlideButton.this.postInvalidate();
                    x += patch;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mDstX = dstX;
                SlideButton.this.state = mDstX > bitmapWidth / 2 ? STATUS_ON : STATUS_OFF;
                SlideButton.this.postInvalidate();
            }
        }

    }

    public void getTextWidthAndHeight(String text) {

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
    }
}
