package com.sample.view.recyclerview;

import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * date: 2020/12/13
 *
 * @author: ice_coffee
 * remark:
 */
class CustomLayoutManager extends RecyclerView.LayoutManager {

    //==============================================================================================
    //  * 必须重写
    //  ~ 唯一 abstract 方法
    //==============================================================================================
    /**
     * * 必须重写
     * 它是 `LayoutManager` 中唯一的 `abstract` 方法, 为子类提供 LayoutParams,
     * 可以使用 RecyclerView.LayoutParams() 对象也可以自定义的 ViewGroup.LayoutParams 对象.
     * 如果自定义话需要重写如下方法:
     * checkLayoutParams（LayoutParams）
     * generateLayoutParams（android.view.ViewGroup.LayoutParams）
     * generateLayoutParams（android.content.Context，android.util.AttributeSet）
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    //==============================================================================================
    //  * 必须重写
    //  ~ 布局相关
    //==============================================================================================

    /**
     * * 必须重写
     * 主要实现 itemview 的 measure 和 layout 过程
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    //==============================================================================================
    //  * 必须重写
    //  ~ 滑动相关
    //==============================================================================================

    /**
     * 是否可以水平滑动
     */
    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    /**
     * 是否可以垂直滑动
     */
    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    /**
     * 控制在水平方向上的滑动距离
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    /**
     * 控制在垂直方向上的滑动距离
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    /**
     * 滑动到适配器指定位置
     */
    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    /**
     * 平滑滚动到适配器指定位置
     * 创建 SmoothScroller 实例并调用 startSmoothScroll()
     */
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }

    //==============================================================================================
    //  ~ 状态相关
    //==============================================================================================

    @Nullable
    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }
}
