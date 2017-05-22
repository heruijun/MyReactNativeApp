package com.myapp.widget.rebound;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.myapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 弹出菜单
 * Created by heruijun on 17/2/17.
 */
public class PopMenu {

    private static final String TAG = "PopMenu";

    /**
     * 默认的列数为4个
     */
    private static final int DEFAULT_COLUMN_COUNT = 3;

    /**
     * 动画时间
     */
    private static final int DEFAULT_DURATION = 500;

    /**
     * 拉力系数
     */
    private static final int DEFAULT_TENSION = 40;

    /**
     * 摩擦力系数
     */
    private static final int DEFAULT_FRICTION = 5;

    /**
     * item水平之间的间距
     */
    private static final int DEFAULT_HORIZONTAL_PADDING = 40;

    /**
     * item竖直之间的间距
     */
    private static final int DEFAULT_VERTICAL_PADDING = 15;

    /**
     * 默认的父容器
     */
    private static final int DEFAULT_MAIN_LAYOUT = -1;

    private Activity mActivity;
    private int mColumnCount;
    private List<PopMenuItem> mMenuItems = new ArrayList<>();
    private List<PopMenuItem> mSubMenuItems = new ArrayList<>();
    private FrameLayout mAnimateLayout;
    private ViewFlipper mViewFlipper;
    private GridLayout mGridLayout1;
    private GridLayout mGridLayout2;
    private int mDuration;
    private double mTension;
    private double mFriction;
    private int mHorizontalPadding;
    private int mVerticalPadding;
    private PopMenuItemListener mPopMenuItemListener;
    private int mResLaout;
    private int mScreenWidth;
    private int mScreenHeight;
    private boolean isShowing = false;
    private View mDivider;
    private TextView mMonthYear, mWeek, mDay;
    private ImageView mBack, mClose1, mClose2;

    private SpringSystem mSpringSystem;

    {
        mSpringSystem = SpringSystem.create();
    }

    private PopMenu(Builder builder) {
        this.mActivity = builder.activity;
        this.mMenuItems.clear();
        this.mMenuItems.addAll(builder.itemList);
        this.mSubMenuItems.clear();
        this.mSubMenuItems.addAll(builder.subItemList);
        this.mColumnCount = builder.columnCount;
        this.mDuration = builder.duration;
        this.mTension = builder.tension;
        this.mFriction = builder.friction;
        this.mHorizontalPadding = builder.horizontalPadding;
        this.mVerticalPadding = builder.verticalPadding;
        this.mPopMenuItemListener = builder.popMenuItemListener;
        this.mResLaout = builder.resLayout;
        mScreenWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mActivity.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 显示日历
     */
    private void showCalendar() {
        LayoutInflater.from(mActivity).inflate(R.layout.layout_calendar_main, mAnimateLayout, true);
        mDivider = mActivity.findViewById(R.id.divide_line);
        mMonthYear = (TextView) mActivity.findViewById(R.id.month_year);
        mWeek = (TextView) mActivity.findViewById(R.id.week);
        mDay = (TextView) mActivity.findViewById(R.id.day);
        mDivider.setRotation(10f);
        Calendar now = Calendar.getInstance();
        Date d = new Date();
        mMonthYear.setText(getMonth(now) + "/" + now.get(Calendar.YEAR));
        mDay.setText(now.get(Calendar.DAY_OF_MONTH) + "");
        mWeek.setText(getWeekOfDate(now, d));
    }

    /**
     * 获取当前月份
     *
     * @param now
     * @return
     */
    public static String getMonth(Calendar now) {
        int month = now.get(Calendar.MONTH) + 1;
        if (month < 10) {
            return "0" + month;
        }
        return "" + month;
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Calendar now, Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        now.setTime(dt);
        int w = now.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 隐藏日历
     */
    private void hideCalendar() {
        mMonthYear.setVisibility(View.GONE);
        mWeek.setVisibility(View.GONE);
        mDay.setVisibility(View.GONE);
    }

    /**
     * 显示第一屏底部
     */
    private void showBottomNav1() {
        mAnimateLayout.removeView(mActivity.findViewById(R.id.bottom_nav2));
        LayoutInflater.from(mActivity).inflate(R.layout.layout_bottom_nav1, mAnimateLayout, true);
        mClose1 = (ImageView) mActivity.findViewById(R.id.close1);
        mClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    hide();
                }
            }
        });
    }

    /**
     * 显示第二屏底部
     */
    private void showBottomNav2() {
        mAnimateLayout.removeView(mActivity.findViewById(R.id.bottom_nav1));
        LayoutInflater.from(mActivity).inflate(R.layout.layout_bottom_nav2, mAnimateLayout, true);
        mBack = (ImageView) mActivity.findViewById(R.id.menu_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrevious();
            }
        });
        mClose2 = (ImageView) mActivity.findViewById(R.id.close2);
        mClose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    hide();
                }
            }
        });
    }

    /**
     * 显示菜单
     */
    public void show() {
        if (mAnimateLayout != null && mAnimateLayout.getChildCount() > 0) {
            mAnimateLayout.removeAllViews();
        }
        mAnimateLayout = (FrameLayout) mActivity.findViewById(mResLaout);
        mViewFlipper = new ViewFlipper(mActivity);

        buildAnimateGridLayout(1, mGridLayout1, mMenuItems);
        buildAnimateGridLayout(2, mGridLayout2, mSubMenuItems);

        mAnimateLayout.addView(mViewFlipper);

        //执行显示动画
        showSubMenus(mGridLayout1);
        showCalendar();
        showBottomNav1();
        isShowing = true;
    }

    /**
     * 隐藏菜单
     */
    public void hide() {
        hideCalendar();
        //先执行消失的动画
        if (isShowing) {
            hideSubMenus(mGridLayout1, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mAnimateLayout != null) {
                        mAnimateLayout.removeAllViews();
                        mAnimateLayout.setVisibility(View.GONE);
                    }
                }
            });
            hideSubMenus(mGridLayout2, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mAnimateLayout != null) {
                        mAnimateLayout.removeAllViews();
                        mAnimateLayout.setVisibility(View.GONE);
                    }
                }
            });
            isShowing = false;
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * 构建动画布局
     */
    private void buildAnimateGridLayout(final int page, GridLayout gridLayout, List<PopMenuItem> menuItems) {
        gridLayout = new GridLayout(mActivity);
        gridLayout.setColumnCount(mColumnCount);
        if (page == 1) {
            mGridLayout1 = gridLayout;
        }
        if (page == 2) {
            mGridLayout2 = gridLayout;
        }

        int hPadding = dp2px(mActivity, mHorizontalPadding);
        int vPadding = dp2px(mActivity, mVerticalPadding);
        int itemWidth = (mScreenWidth - (mColumnCount + 1) * hPadding) / mColumnCount;

        int rowCount = menuItems.size() % mColumnCount == 0 ? menuItems.size() / mColumnCount :
                menuItems.size() / mColumnCount + 1;

        // 出现在距离屏幕底部1/3位置
        int topMargin = (mScreenHeight - (itemWidth + vPadding) * rowCount + vPadding) / 2;

        // 添加每个按钮
        for (int i = 0; i < menuItems.size(); i++) {
            final int position = i;
            PopSubView subView = new PopSubView(mActivity);
            PopMenuItem menuItem = menuItems.get(i);
            subView.setPopMenuItem(menuItem);
            subView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopMenuItemListener != null) {
                        mPopMenuItemListener.onItemClick(page, PopMenu.this, position);
                    }
                }
            });

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = itemWidth;
            lp.leftMargin = hPadding;
            if (i / mColumnCount == 0) {
                lp.topMargin = topMargin;
            } else {
                lp.topMargin = vPadding;
            }
            gridLayout.addView(subView, lp);
        }

        mViewFlipper.addView(gridLayout);
    }

    /**
     * 上一屏
     */
    public void showPrevious() {
        mViewFlipper.setInAnimation(mActivity, R.anim.push_right_in);
        mViewFlipper.setOutAnimation(mActivity, R.anim.push_right_out);
        mViewFlipper.showPrevious();
        showBottomNav1();
    }

    /**
     * 下一屏
     */
    public void showNext() {
        mViewFlipper.setInAnimation(mActivity, R.anim.push_left_in);
        mViewFlipper.setOutAnimation(mActivity, R.anim.push_left_out);
        mViewFlipper.showNext();
        showBottomNav2();
    }

    /**
     * 动画显示菜单
     *
     * @param viewGroup
     */
    private void showSubMenus(ViewGroup viewGroup) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            animateViewDirection(view, mScreenHeight, 0, mTension, mFriction, i);
        }
    }

    /**
     * 动画隐藏菜单
     *
     * @param viewGroup
     * @param listener
     */
    private void hideSubMenus(ViewGroup viewGroup, final AnimatorListenerAdapter listener) {
        if (viewGroup == null) return;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.animate().translationY(mScreenHeight).setDuration(mDuration).setListener(listener).start();
        }
    }

    /**
     * 弹簧动画
     *
     * @param v 动画View
     * @param from
     * @param to
     * @param tension 拉力系数
     * @param friction 摩擦力系数
     */
    private void animateViewDirection(final View v, float from, float to, double tension, double friction, final int index) {
        Spring spring = mSpringSystem.createSpring();
        spring.setCurrentValue(from * (index + 1));
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                v.setTranslationY(value);
            }
        });
        spring.setEndValue(to);
    }

    public static class Builder {
        private Activity activity;
        private int columnCount = DEFAULT_COLUMN_COUNT;
        private List<PopMenuItem> itemList = new ArrayList<>();
        private List<PopMenuItem> subItemList = new ArrayList<>();
        private int duration = DEFAULT_DURATION;
        private double tension = DEFAULT_TENSION;
        private double friction = DEFAULT_FRICTION;
        private int horizontalPadding = DEFAULT_HORIZONTAL_PADDING;
        private int verticalPadding = DEFAULT_VERTICAL_PADDING;
        private int resLayout = DEFAULT_MAIN_LAYOUT;
        private PopMenuItemListener popMenuItemListener;

        public Builder attachToActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder resLayout(@IdRes int resLayout) {
            this.resLayout = resLayout;
            return this;
        }

        public Builder columnCount(int count) {
            this.columnCount = count;
            return this;
        }

        public Builder addMenuItem(PopMenuItem menuItem) {
            this.itemList.add(menuItem);
            return this;
        }

        public Builder addSubMenuItem(PopMenuItem menuItem) {
            this.subItemList.add(menuItem);
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder tension(double tension) {
            this.tension = tension;
            return this;
        }

        public Builder friction(double friction) {
            this.friction = friction;
            return this;
        }

        public Builder horizontalPadding(int padding) {
            this.horizontalPadding = padding;
            return this;
        }

        public Builder verticalPadding(int padding) {
            this.verticalPadding = padding;
            return this;
        }

        public Builder setOnItemClickListener(PopMenuItemListener listener) {
            this.popMenuItemListener = listener;
            return this;
        }

        public PopMenu build() {
            final PopMenu popMenu = new PopMenu(this);
            return popMenu;
        }
    }

    /**
     * dp 2 px
     *
     * @param context
     * @param dpVal
     * @return
     */
    protected int dp2px(Context context, int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}
