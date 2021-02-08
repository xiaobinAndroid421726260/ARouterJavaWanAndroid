//package com.dbz.system.view;
//
//import android.content.Context;
//import android.database.DataSetObserver;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FlowLayout extends ViewGroup {
//
//    protected DataSetObserver mDataSetObserver;
//    private FlowBaseAdapter mAdapter;
//    /**
//     * 所有的子view按行存储
//     */
//    private final List<List<View>> mAllChildViews;
//    /**
//     * 所有行高
//     */
//    private final List<Integer> mLineHeights;
//
//    public FlowLayout(Context context) {
//        this(context, null);
//    }
//
//    public FlowLayout(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        mAllChildViews = new ArrayList<>();
//        mLineHeights = new ArrayList<>();
//        mDataSetObserver = new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                resetLayout();
//            }
//        };
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // 获取XML设置的大小和测量模式
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
//        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
//
//        if (modeWidth == MeasureSpec.AT_MOST) {
//            throw new RuntimeException("FlowLayout: layout_width  must not  be set to wrap_content !!!");
//        }
//
//        int height = getPaddingTop() + getPaddingBottom();
//        // 行宽
//        int lineWidth = 0;
//        // 行高
//        int lineHeight = 0;
//        int childCount = getChildCount();
//
//        mAllChildViews.clear();
//        mLineHeights.clear();
//        List<View> lineViews = new ArrayList<>();
//
//        for (int i = 0; i < childCount; i++) {
//            View childView = getChildAt(i);
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//
//            MarginLayoutParams lp = (MarginLayoutParams) childView
//                    .getLayoutParams();
//            int childLineWidth = childView.getMeasuredWidth() + lp.leftMargin
//                    + lp.rightMargin;
//            int childLineHeight = childView.getMeasuredHeight() + lp.topMargin
//                    + lp.bottomMargin;
//
//            // 考虑padding
//            if (childLineWidth + lineWidth > (widthSize - getPaddingRight() - getPaddingLeft())) {
//                // 换行
//                height += lineHeight;
//                lineWidth = childLineWidth;
//
//                // 添加子View到集合
//                mAllChildViews.add(lineViews);
//                mLineHeights.add(lineHeight);
//
//                lineViews = new ArrayList<View>();
//                lineViews.add(childView);
//            } else {
//                // 不换行
//                lineHeight = Math.max(childLineHeight, lineHeight);
//                lineWidth += childLineWidth;
//
//                lineViews.add(childView);
//            }
//
//            //添加最后一行
//            if (i == childCount - 1) {
//                height += lineHeight;
//
//                mLineHeights.add(lineHeight);
//                mAllChildViews.add(lineViews);
//            }
//        }
//
//        setMeasuredDimension(widthSize,
//                modeHeight == MeasureSpec.AT_MOST ? height : heightSize);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        // 设置子View的位置
//        int left = getPaddingLeft();
//        int top = getPaddingTop();
//        // 行数
//        int lineNumber = mAllChildViews.size();
//
//        for (int i = 0; i < lineNumber; i++) {
//            List<View> lineViews = mAllChildViews.get(i);
//            int lineHeight = mLineHeights.get(i);
//            for (int j = 0; j < lineViews.size(); j++) {
//                View child = lineViews.get(j);
//                if (child.getVisibility() == View.GONE) {
//                    continue;
//                }
//                MarginLayoutParams lp = (MarginLayoutParams) child
//                        .getLayoutParams();
//                int lc = left + lp.leftMargin;
//                int tc = top + lp.topMargin;
//                int rc = lc + child.getMeasuredWidth();
//                int bc = tc + child.getMeasuredHeight();
//                child.layout(lc, tc, rc, bc);
//
//                left += child.getMeasuredWidth() + lp.rightMargin
//                        + lp.leftMargin;
//            }
//            left = getPaddingLeft();
//            top += lineHeight;
//        }
//    }
//
//    /**
//     * 重写generateLayoutParams()
//     *
//     * @param attrs attrs
//     * @return MarginLayoutParams
//     */
//    @Override
//    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new MarginLayoutParams(getContext(), attrs);
//    }
//
//    public void setAdapter(FlowBaseAdapter adapter) {
//        if (mAdapter != null && mDataSetObserver != null) {
//            mAdapter.unregisterDataSetObserver(mDataSetObserver);
//            mAdapter = null;
//        }
//
//        if (adapter == null) {
//            throw new NullPointerException("adapter is null");
//        }
//
//        this.mAdapter = adapter;
//
//        mAdapter.registerDataSetObserver(mDataSetObserver);
//
//        resetLayout();
//    }
//
//    /**
//     * 重新Layout子View
//     */
//    protected final void resetLayout() {
//        this.removeAllViews();
//        int counts = mAdapter.getCounts();
//        mAdapter.addViewToList(this);
//
//        ArrayList<View> views = mAdapter.getViewList();
//
//        for (int i = 0; i < counts; i++) {
//            this.addView(views.get(i));
//        }
//    }
//}
