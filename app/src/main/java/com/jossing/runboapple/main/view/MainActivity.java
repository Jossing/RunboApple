package com.jossing.runboapple.main.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.jossing.runboapple.R;
import com.jossing.runboapple.main.presenter.IMainPresenter;
import com.jossing.runboapple.main.presenter.MainPresenter;
import com.jossing.runboapple.usermanage.view.MineFragment;
import com.jossing.runboapple.promotion.view.PromotionFragment;

/**
 * @author Jossing , Create on 2017/3/16
 */

public class MainActivity extends AppCompatActivity
        implements IMainActivity, OnMenuItemClickListener {

    private IMainPresenter presenter;

    private BottomNavigationView bnv;
    private AppleFragment appleFragment;
    private PromotionFragment promotionFragment;
    private MineFragment mineFragment;

    // 当前显示的 fragment
    private Fragment fromFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        initWidget();

        // 默认显示 AppleFragment
        switchFragment(appleFragment);
    }

    private void initWidget() {
        // 实例化 fragment
        appleFragment = new AppleFragment();
        promotionFragment = new PromotionFragment();
        mineFragment = new MineFragment();
        // fixSomeFragmentBug(); 然而这个方法并没有什么用
        // 初始化 BottomNavigationView
        bnv = (BottomNavigationView) findViewById(R.id.bnv);
        Menu bnvMenu = bnv.getMenu();
        bnvMenu.findItem(R.id.bnv_menu1).setOnMenuItemClickListener(this);
        bnvMenu.findItem(R.id.bnv_menu2).setOnMenuItemClickListener(this);
        bnvMenu.findItem(R.id.bnv_menu3).setOnMenuItemClickListener(this);
    }

    /**
     * 切换 fragment
     * @param toFragment 将要显示的 fragment
     */
    private void switchFragment(Fragment toFragment) {
        if (fromFragment != null) {
            // 如果当前显示的 fragment 存在
            if (fromFragment.equals(toFragment)) {
                // 但却和将要显示的相同，那么就什么也不做
                return;
            } else if (!toFragment.isAdded()) {
                // 并且将要显示的 fragment 未被添加过
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(fromFragment) // 隐藏当前显示的
                        .add(R.id.fl_fragment, toFragment) // 添加并显示将要显示的
                        .commit();
            } else {
                // 并且将要显示的 fragment 也存在
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(fromFragment) // 隐藏当前显示的
                        .show(toFragment) // 显示出即将要显示的
                        .commit();
            }
        } else {
            // 如果当前显示的 fragment 不存在（一般在第一次挑用这个方法时）
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .add(R.id.fl_fragment, toFragment) // 直接显示将要显示的即可
                    .commit();
        }
        fromFragment = toFragment; // 更新当前 fragment
    }

    /**
     * 希望可以避免一些奇怪的问题
     */
    private void fixSomeFragmentBug() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(appleFragment)
                .remove(promotionFragment)
                .remove(mineFragment)
                .commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bnv_menu1:
                switchFragment(appleFragment);
                break;
            case R.id.bnv_menu2:
                switchFragment(promotionFragment);
                break;
            case R.id.bnv_menu3:
                switchFragment(mineFragment);
                break;
        }
        return false;
    }
}
