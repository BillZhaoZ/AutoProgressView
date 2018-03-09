package com.zhao.bill.autoprogressview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 自动加载的柱状进度条
 * 特性：支持横向和竖向加载，并可以自定义设置进度条背景颜色和背景图片，加载速度动画和控件的宽高。
 */
public class AutoProgressView extends View implements Runnable {

    private static final String TAG = AutoProgressView.class.getSimpleName();

    private int comWidth; //控件宽度
    private int comHeight;//控件高度

    private View rateView;//进度条
    private int rateHeight; //进度条的高
    private int rateWidth; //进度条的宽
    private View rateTopView; //进度条顶部View

    private String rateBackgroundColor;//进图条背景颜色
    private int rateBackgroundId; //进图条背景图片id
    private Bitmap rataBackgroundBitmap;
    private boolean isHasRateTopView; //进度条顶部View

    private int rateAnimValue;//进度条动画高度
    private int orientation; //设置柱状图方向
    private double progress;//设置进度  1为最大值
    private boolean isAnim = true; //是否动画显示统计条

    private Handler handler = new Handler();//动画handler
    private int animRate = 1; //动画速度   以每1毫秒计
    private int animTime = 0;//动画延迟执行时间
    private Canvas canvas;//画布

    public AutoProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoProgressView(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //初始化控件和进度的条相关参数
        comWidth = w;
        comHeight = h;

        if (orientation == LinearLayout.HORIZONTAL) {
            rateWidth = (int) (w * progress);
            rateHeight = h;
        } else {
            rateHeight = (int) (h * progress);
            rateWidth = w;
        }
    }

    /**
     * 增加重新摆放的方法  测量位置
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        comWidth = right - left;
        comHeight = bottom - top;

        if (orientation == LinearLayout.HORIZONTAL) {
            rateWidth = (int) ((right - left) * progress);
            rateHeight = bottom - top;
        } else {
            rateHeight = (int) ((bottom - top) * progress);
            rateWidth = right - left;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        Log.d(TAG, "onDraw  rateBackgroundColor====" + rateBackgroundColor);

        if (rateBackgroundColor != null) {
            drawViewWithColor(paint, isAnim);
        } else if (rateBackgroundId != -1) {
            drawViewWithBitmap(paint, isAnim);
        }

    }

    /**
     * drawViewWithColor:(绘制颜色进度条)
     *
     * @param paint
     * @param isAnim
     * @author msl
     * @since 1.0
     */
    private void drawViewWithColor(Paint paint, boolean isAnim) {
        paint.setColor(Color.parseColor(rateBackgroundColor));

        Log.d(TAG, "rateBackgroundColor====" + rateBackgroundColor);

        if (isAnim) {
            handler.postDelayed(this, animTime);

            if (orientation == LinearLayout.HORIZONTAL) {//水平方向柱状图
                canvas.drawRect(0, 0, rateAnimValue, comHeight, paint);
            } else {//垂直方向柱状图
                canvas.drawRect(0, comHeight - rateAnimValue, comWidth, comHeight, paint);
            }
        } else {
            if (orientation == LinearLayout.HORIZONTAL) {//水平方向无动画柱状图
                canvas.drawRect(0, 0, rateWidth, comHeight, paint);
            } else {//垂直方向无动画柱状图
                canvas.drawRect(0, comHeight - rateHeight, comWidth, comHeight, paint);
            }
        }

    }

    /**
     * drawViewWithBitmap:(绘制图片进度条)
     *
     * @param paint
     * @param isAnim
     * @author msl
     * @since 1.0
     */
    private void drawViewWithBitmap(Paint paint, boolean isAnim) {
        Log.d(TAG, "bitmap====" + rataBackgroundBitmap);

        RectF dst = null;

        if (isAnim) {
            handler.postDelayed(this, animTime);
            if (orientation == LinearLayout.HORIZONTAL) {//水平方向柱状图
                dst = new RectF(0, 0, rateAnimValue, comHeight);
                canvas.drawBitmap(rataBackgroundBitmap, null, dst, paint);
            } else {//垂直方向柱状图
                dst = new RectF(0, comHeight - rateAnimValue, comWidth, comHeight);
                canvas.drawBitmap(rataBackgroundBitmap, null, dst, paint);
            }
        } else {
            if (orientation == LinearLayout.HORIZONTAL) {//水平方向无动画柱状图
                dst = new RectF(0, 0, rateWidth, comHeight);
                canvas.drawBitmap(rataBackgroundBitmap, null, dst, paint);
            } else {//垂直方向无动画柱状图
                dst = new RectF(0, comHeight - rateHeight, comWidth, comHeight);
                canvas.drawBitmap(rataBackgroundBitmap, null, dst, paint);
            }
        }
    }

    /**
     * 刷新界面
     *
     * @see Runnable#run()
     */
    @Override
    public void run() {
        if (orientation == LinearLayout.HORIZONTAL && (rateAnimValue <= rateWidth)) {
            rateAnimValue += animRate;
            invalidate();
        } else if (orientation == LinearLayout.VERTICAL && (rateAnimValue <= rateHeight)) {
            rateAnimValue += animRate;
            invalidate();
        } else {
            handler.removeCallbacks(this);
            rateAnimValue = 0;
        }
    }

    /**
     * 对外提供一系列的设置方法
     *
     * @return
     */

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public View getRateView() {
        return rateView;
    }

    public void setRateView(View rateView) {
        this.rateView = rateView;
    }

    public int getRateHeight() {
        return rateHeight;
    }

    public void setRateHeight(int rateHeight) {
        this.rateHeight = rateHeight;
    }

    public int getRateWidth() {
        return rateWidth;
    }

    public void setRateWidth(int rateWidth) {
        this.rateWidth = rateWidth;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean isAnim) {
        this.isAnim = isAnim;
    }

    public int getAnimRate() {
        return animRate;
    }

    public void setAnimRate(int animRate) {
        this.animRate = animRate;
    }

    public String getRateBackgroundColor() {
        return rateBackgroundColor;
    }

    public void setRateBackgroundColor(String rateBackgroundColor) {
        this.rateBackgroundColor = rateBackgroundColor;
        rateBackgroundId = -1;
        rataBackgroundBitmap = null;
    }

    public int getRateBackgroundId() {
        return rateBackgroundId;
    }

    public void setRateBackgroundId(int rateBackground) {
        this.rateBackgroundId = rateBackground;
        rataBackgroundBitmap = BitmapFactory.decodeResource(getResources(), rateBackgroundId);
        rateBackgroundColor = null;
    }

}

