package com.jossing.runboapple.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.jossing.runboapple.R;

/**
 * @author Jossing , Create on 2017/4/6
 */

public class ViewPagerWithIndicator extends RelativeLayout {
    private float density;

    private ViewPager viewPager;

    private int indicatorGravity;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_TOP = 2;
    public static final int GRAVITY_RIGHT = 3;
    public static final int GRAVITY_BOTTOM = 4;

    public ViewPagerWithIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerWithIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerWithIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        density = context.getResources().getDisplayMetrics().density;
        indicatorGravity = GRAVITY_BOTTOM;

        initViewPager(context);
        initIndicator(context);
    }

    private void initViewPager(Context context) {
        if (viewPager == null) viewPager = new ViewPager(context);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(viewPager);
    }

    private void initIndicator(Context context) {
        Indicator indicator = new Indicator(context);
        indicator.setDotRadius(4 * density);
        indicator.setDotSpacing(8 * density);
        indicator.setOrientation(Indicator.HORIZONTAL);
        int padding = Math.round(indicator.getDotSpacing());
        indicator.setPadding(padding, padding, padding, padding);
        indicator.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(indicator);
        viewPager.addOnPageChangeListener(indicator);
        viewPager.addOnAdapterChangeListener(indicator);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 布局 ViewPager
        getChildAt(0).layout(0, 0, right, bottom);

        // 布局 Indicator
        int indicatorWidth = getChildAt(1).getMeasuredWidth();
        int indicatorHeight = getChildAt(1).getMeasuredHeight();
        int indicatorLeft = (getMeasuredWidth() - indicatorWidth) / 2;
        int indicatorTop = getMeasuredHeight() - getPaddingBottom() - indicatorHeight;
        switch (indicatorGravity) {
            case GRAVITY_BOTTOM:
                getChildAt(1).layout(indicatorLeft, indicatorTop,
                        indicatorLeft + indicatorWidth, indicatorTop + indicatorHeight);
                break;
        }
    }




    /**
     * 指示器视图
     * @author Jossing , Create on 2017/4/6
     */
    public class Indicator extends View
            implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {

        private int dotCount; // 点的个数
        private float dotRadius; // 点的半径
        private float dotSpacing; // 点之间的间距

        private int selectedDot;
        float selectedDotOffsetDistance;

        private int orientation; // 排列方向
        public static final int HORIZONTAL = 1;
        public static final int VERTICAL = 2;

        private Paint paint;

        public Indicator(Context context) {
            this(context, null);
        }

        public Indicator(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
            dotCount = a.getInteger(R.styleable.Indicator_dotCount, 1); // 默认个数为 1
            float defDotRadius = 4 * density; // 默认半径 4dp
            dotRadius = a.getDimension(R.styleable.Indicator_dotRadius, defDotRadius);
            float defDotSpacing = 8 * density; // 默认间距 8dp
            dotSpacing = a.getDimension(R.styleable.Indicator_dotSpacing, defDotSpacing);
            orientation = a.getInt(R.styleable.Indicator_orientation, HORIZONTAL); // 默认水平排列

            a.recycle();

            selectedDot = 0;
            selectedDotOffsetDistance = dotRadius * 2 + dotSpacing;

            paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            int measuredWidth = 0;
            int measuredHeight = 0;

            /* 测量 view 的宽度 */
            if (widthMode == MeasureSpec.EXACTLY) {
                measuredWidth = widthSize;
            } else {
                if (orientation == HORIZONTAL) {
                    // 水平排列
                    measuredWidth = getPaddingLeft() + getPaddingRight() // 左右内边距
                            + dotCount * Math.round(dotRadius * 2) // 所有 dot 的直径之和
                            + Math.round((dotCount - 1) * dotSpacing); // 间距之和
                } else {
                    // 垂直排列
                    measuredWidth = getPaddingLeft() + getPaddingRight() // 左右内边距
                            + Math.round(dotRadius * 2); // dot 的直径
                }
            }

            /* 测量 view 的高度 */
            if (heightMode == MeasureSpec.EXACTLY) {
                measuredHeight = heightSize;
            } else {
                if (orientation == HORIZONTAL) {
                    // 水平排列
                    measuredHeight = getPaddingTop() + getPaddingBottom() // 上下内边距
                            + Math.round(dotRadius * 2); // dot 的直径
                } else {
                    // 垂直排列
                    measuredHeight = getPaddingTop() + getPaddingTop() // 左右内边距
                            + dotCount * Math.round(dotRadius * 2) // 所有 dot 的直径之和
                            + Math.round((dotCount - 1) * dotSpacing); // 间距之和
                }
            }

            setMeasuredDimension(measuredWidth, measuredHeight);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            /* 绘制指示器 dot */
            int secondaryWhite = Color.argb(0x8a, 255, 255, 255);
            paint.setColor(secondaryWhite);
            if (orientation == HORIZONTAL) {
                // 水平排列
                float cx = getPaddingLeft() + dotRadius;
                float cy = getHeight() / 2f;
                for (int i = 0; i < dotCount; i++) {
                    if (i == selectedDot) {
                        paint.setColor(getResources().getColor(R.color.colorAccent));
                        canvas.drawCircle(cx, cy, dotRadius, paint);
                        paint.setColor(secondaryWhite);
                    } else {
                        canvas.drawCircle(cx, cy, dotRadius, paint);
                    }
                    cx += dotSpacing + dotRadius * 2;
                }
            } else {
                // 垂直排列
                float cx = getWidth() / 2f;
                float cy = getPaddingTop() + dotRadius;
                for (int i = 0; i < dotCount; i++) {
                    if (i == selectedDot) {
                        paint.setColor(getResources().getColor(R.color.colorAccent));
                        canvas.drawCircle(cx, cy, dotRadius, paint);
                        paint.setColor(secondaryWhite);
                    } else {
                        canvas.drawCircle(cx, cy, dotRadius, paint);
                    }
                    cy += dotSpacing + dotRadius * 2;
                }
            }
        }

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
            if (newAdapter != null) {
                dotCount = newAdapter.getCount();
                selectedDot = 0;

                invalidate();
                requestLayout();
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            selectedDot = position;
            invalidate();
            requestLayout();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        public void setDotCount(int dotCount) {
            this.dotCount = dotCount;
        }

        public void setDotRadius(float dotRadius) {
            this.dotRadius = dotRadius;
        }

        public void setDotSpacing(float dotSpacing) {
            this.dotSpacing = dotSpacing;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public int getDotCount() {
            return dotCount;
        }

        public float getDotRadius() {
            return dotRadius;
        }

        public float getDotSpacing() {
            return dotSpacing;
        }

        public int getOrientation() {
            return orientation;
        }
    }

}
