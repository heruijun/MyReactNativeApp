package com.myapp.widget.rebound;

/**
 * Created by heruijun on 17/2/18.
 */
public interface PopMenuItemListener {

    /**
     * 点击菜单回调
     *
     * @param page
     * @param popMenu
     * @param position
     */
    public void onItemClick(int page, PopMenu popMenu, int position);
}
