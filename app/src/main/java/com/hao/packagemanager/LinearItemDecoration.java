package com.hao.packagemanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 线性布局分割线
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 分割线对齐类型：
     * FULL:整个宽度；
     * CENTER：左右间距16dp
     * RIGHT：靠右对齐，左侧间距16dp
     * LEFT：靠左对齐，左侧间距16dp
     * TOP：靠上对齐，底部间距16dp
     * BOTTOM：靠下对齐，顶部间距16dp
     */
    public enum DividerType {
        FULL, CENTER, RIGHT, LEFT, TOP, BOTTOM
    }

    private Context mContext;
    private int mDividerSize;
    private DividerType mDividerType;
    private Paint mPaint;
    private int mOrientation;

    public LinearItemDecoration(@NonNull Context context, @NonNull int orientation, @DimenRes int dividerHeightResId, @ColorRes int dividerColorResId) {
        this(context, orientation, DividerType.FULL, dividerHeightResId, dividerColorResId);
    }

    public LinearItemDecoration(Context context, int orientation, DividerType dividerType, @DimenRes int dividerSizeResId, @ColorRes int dividerColorResId) {
        this.mContext = context;
        this.mDividerSize = context.getResources().getDimensionPixelSize(dividerSizeResId);
        this.mDividerType = dividerType;
        this.mPaint = new Paint();
        this.mPaint.setColor(ContextCompat.getColor(context, dividerColorResId));
        this.mPaint.setStyle(Paint.Style.FILL);
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        final int margin = dp2px(16);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + mDividerSize;
            switch (mDividerType) {
                case CENTER:
                    canvas.drawRect(left + margin, top, right - margin, bottom, mPaint);
                    break;
                case LEFT:
                    canvas.drawRect(left, top, right - margin, bottom, mPaint);
                    break;
                case RIGHT:
                    canvas.drawRect(left + margin, top, right, bottom, mPaint);
                    break;
                default:
                    canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        final int margin = dp2px(16);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin + Math.round(ViewCompat.getTranslationX(child));
            final int right = left + mDividerSize;
            switch (mDividerType) {
                case CENTER:
                    canvas.drawRect(left, top + margin, right, bottom - margin, mPaint);
                    break;
                case TOP:
                    canvas.drawRect(left, top, right, bottom - margin, mPaint);
                    break;
                case BOTTOM:
                    canvas.drawRect(left, top + margin, right, bottom, mPaint);
                    break;
                default:
                    canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDividerSize);
        } else {
            outRect.set(0, 0, mDividerSize, 0);
        }
    }

    private int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int px2dp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}