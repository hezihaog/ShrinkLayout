package com.github.wally.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Package: com.github.wally.custom
 * FileName: ShrinkItem
 * Date: on 2018/6/21  上午8:52
 * Auther: zihe
 * Descirbe:收缩布局
 * Email: hezihao@linghit.com
 */
public class ShrinkLayout extends FrameLayout {
    private int mOriginViewWidth;
    private int mOriginViewHeight;
    //标志判断是否已经获取到了原始高度
    private boolean isGetOriginParams = false;

    public ShrinkLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public ShrinkLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShrinkLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //因为每次改变高度，这里都会回调，所以只获取最开始的高度宽度参数
        if (!isGetOriginParams) {
            this.mOriginViewWidth = w;
            this.mOriginViewHeight = h;
            this.isGetOriginParams = true;
        }
    }

    /**
     * 压缩
     *
     * @param originHeight 原始高度
     * @param targetHeight 目标高度
     */
    public void shrink(int originHeight, int targetHeight) {
        ChangeHeight(originHeight, targetHeight);
    }

    /**
     * 恢复高度
     */
    public void restore() {
        ChangeHeight(getHeight(), mOriginViewHeight);
    }

    /**
     * 获取布局一开始的高度
     */
    public int getOrginHeight() {
        return mOriginViewHeight;
    }

    private void ChangeHeight(int originHeight, int targetHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(originHeight, targetHeight);
        animator.setDuration(550);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer cValue = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = getLayoutParams();
                params.height = cValue;
                requestLayout();
            }
        });
        animator.start();
    }
}