package com.myapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.react.modules.core.PermissionListener;
import com.hrj.rn.runtime.common.app.BaseReactActivity;
import com.imagepicker.permissions.OnImagePickerPermissionsCallback;
import com.myapp.R;
import com.myapp.fragment.HomeFragment;
import com.myapp.fragment.MessageFragment;
import com.myapp.fragment.MineFragment;
import com.myapp.fragment.RoomFragment;
import com.myapp.widget.BottomNavigationViewHelper;
import com.myapp.widget.rebound.PopMenu;
import com.myapp.widget.rebound.PopMenuItem;
import com.myapp.widget.rebound.PopMenuItemListener;

public class MainActivity extends BaseReactActivity implements OnImagePickerPermissionsCallback {

    private FragmentManager fm;
    private Fragment mCommonFragmentOne;
    private Fragment mCurrent;
    private HomeFragment mHomeFragment;
    private RoomFragment mRoomFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private RelativeLayout mTabLayout;
    private FrameLayout mMainLayout;
    private PopMenu mPopMenu;
    private final static int MENU_FIRST = 1;       // 第一屏
    private final static int MENU_SECOND = 2;      // 第二屏

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_fast:
                    hideFragment(mCommonFragmentOne, fragmentTransaction);
                    hideFragment(mRoomFragment, fragmentTransaction);
                    hideFragment(mMessageFragment, fragmentTransaction);
                    hideFragment(mMineFragment, fragmentTransaction);
                    if (mHomeFragment == null) {
                        mHomeFragment = new HomeFragment();
                        fragmentTransaction.add(R.id.content, mHomeFragment);
                    } else {
                        mCurrent = mHomeFragment;
                        fragmentTransaction.show(mHomeFragment);
                    }
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_home:
                    hideFragment(mCommonFragmentOne, fragmentTransaction);
                    hideFragment(mHomeFragment, fragmentTransaction);
                    hideFragment(mMessageFragment, fragmentTransaction);
                    hideFragment(mMineFragment, fragmentTransaction);
                    if (mRoomFragment == null) {
                        mRoomFragment = new RoomFragment();
                        fragmentTransaction.add(R.id.content, mRoomFragment);
                    } else {
                        mCurrent = mRoomFragment;
                        fragmentTransaction.show(mRoomFragment);
                    }
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_manager:
                    hideFragment(mCommonFragmentOne, fragmentTransaction);
                    hideFragment(mRoomFragment, fragmentTransaction);
                    hideFragment(mHomeFragment, fragmentTransaction);
                    hideFragment(mMineFragment, fragmentTransaction);
                    if (mMessageFragment == null) {
                        mMessageFragment = new MessageFragment();
                        fragmentTransaction.add(R.id.content, mMessageFragment);
                    } else {
                        mCurrent = mMessageFragment;
                        fragmentTransaction.show(mMessageFragment);
                    }
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_mine:
                    hideFragment(mCommonFragmentOne, fragmentTransaction);
                    hideFragment(mRoomFragment, fragmentTransaction);
                    hideFragment(mMessageFragment, fragmentTransaction);
                    hideFragment(mHomeFragment, fragmentTransaction);
                    if (mMineFragment == null) {
                        mMineFragment = new MineFragment();
                        fragmentTransaction.add(R.id.content, mMineFragment);
                    } else {
                        mCurrent = mMineFragment;
                        fragmentTransaction.show(mMineFragment);
                    }
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mHomeFragment = new HomeFragment();
        fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content, mHomeFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        mTabLayout = (RelativeLayout) findViewById(R.id.tab_layout_view);
        // 初始化菜单
        mMainLayout = (FrameLayout) findViewById(R.id.main_layout);
        mPopMenu = new PopMenu.Builder().columnCount(3).friction(10).resLayout(R.id.main_layout).attachToActivity(MainActivity.this)
                .addMenuItem(new PopMenuItem("添加房源", getResources().getDrawable(R.drawable.menu_1)))
                .addMenuItem(new PopMenuItem("营业报表", getResources().getDrawable(R.drawable.menu_2)))
                .addMenuItem(new PopMenuItem("RN组件", getResources().getDrawable(R.drawable.menu_3)))
                .addMenuItem(new PopMenuItem("分散式房源发布管理", getResources().getDrawable(R.drawable.menu_4)))
                .addSubMenuItem(new PopMenuItem("集中式房源", getResources().getDrawable(R.drawable.submenu_1)))
                .addSubMenuItem(new PopMenuItem("分散式房源合租", getResources().getDrawable(R.drawable.submenu_2)))
                .addSubMenuItem(new PopMenuItem("分散式房源整租", getResources().getDrawable(R.drawable.submenu_3)))
                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(int page, PopMenu popMenu, int position) {
                        if (page == MENU_FIRST) {
                            if (position == 0) {
                                mPopMenu.showNext();
                            } else {
                                if (position == 1) {
                                    Intent intent = new Intent(MainActivity.this, RNActivity.class);
                                    startActivity(intent);
                                } else if (position == 2) {
                                    Intent intent = new Intent(MainActivity.this, RNComponentActivity.class);
                                    startActivity(intent);
                                }
                                mPopMenu.hide();
                            }
                        } else if (page == MENU_SECOND) {
                            mPopMenu.hide();
                        }
                    }
                })
                .build();

        mTabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPopMenu.isShowing()) {
                    mMainLayout.setVisibility(View.VISIBLE);
                    mPopMenu.show();
                }
            }
        });
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }

    private PermissionListener listener;

    @Override
    public void setPermissionListener(PermissionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (listener != null) {
            listener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (mPopMenu.isShowing()) {
            mPopMenu.hide();
            return;
        } else {
            super.onBackPressed();
        }
    }
}
