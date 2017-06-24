package com.jossing.runboapple.ordermanage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.order.model.Order;
import com.jossing.runboapple.ordermanage.adapter.SimpleFragmentPagerAdapter;
import com.jossing.runboapple.ordermanage.presenter.IMyOrderPresenter;
import com.jossing.runboapple.ordermanage.presenter.MyOrderPresenter;
import com.jossing.runboapple.usermanage.model.User;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * @author Jossing , Create on 2017/4/16
 */

public class MyOrderActivity extends AppCompatActivity
        implements IMyOrderActivity, RightFragment.Listener, LeftFragment.Listener {

    private IMyOrderPresenter presenter;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tab;
    private SimpleFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MyOrderPresenter(this);
        setContentView(R.layout.activity_my_order);
        initToolbar();
        initWidget();

    }

    private void initWidget() {
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.vp_my_orders);
        viewPager.setAdapter(pagerAdapter);
        tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(viewPager, true);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void queryOrders(String seller_buyer) {
        User currentUser = BmobUser.getCurrentUser(this, User.class);
        if (currentUser == null) {
            RunboAppleApp.toastShow(this, "你可能还没有登陆？", Toast.LENGTH_LONG);
            return;
        }
        presenter.queryOrders(this, seller_buyer, currentUser);
    }

    @Override
    public void onQueryOrdersDone(List<Order> orders, String msg, String seller_buyer) {
        if (orders == null) {
            RunboAppleApp.toastShow(this, msg, Toast.LENGTH_LONG);
            return;
        }

        Log.e("on query orders done", "orders size : " + orders.size());
        if (seller_buyer.equals("seller")) {
            RightFragment right = (RightFragment) pagerAdapter.getItem(1);
            right.onQueryOrdersDone(orders);
        } else if (seller_buyer.equals("buyer")) {
            LeftFragment left = (LeftFragment) pagerAdapter.getItem(0);
            left.onQueryOrdersDone(orders);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }
}
