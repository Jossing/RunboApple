package com.jossing.runboapple.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.main.adapter.AppleAdapter;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.presenter.AppleFGPresenter;
import com.jossing.runboapple.main.presenter.IAppleFGPresenter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Jossing , Create on 2017/3/18
 */

public class AppleFragment extends Fragment
        implements IAppleFragment, SwipeRefreshLayout.OnRefreshListener {

    private IAppleFGPresenter presenter;

    private View rootView;
    public Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    public RecyclerView rvApple;
    public AppleAdapter appleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new AppleFGPresenter(this);
        rootView = inflater.inflate(R.layout.fragment_apple, container, false);
        initWidget();

        delayToRefresh();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 启用 fragment 菜单
        setHasOptionsMenu(true);
    }

    private void initWidget() {
        // 配置 toolbar
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("苹果");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        // 配置列表
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        rvApple = (RecyclerView) rootView.findViewById(R.id.rv_apple);
        rvApple.setItemAnimator(new DefaultItemAnimator());
        rvApple.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvApple.setLayoutManager(linearLayoutManager);
        appleAdapter = new AppleAdapter(getActivity(), rvApple);
        rvApple.setAdapter(appleAdapter);
    }

    /**
     * 延迟 120ms 后主动刷新
     */
    private void delayToRefresh() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity().isFinishing() || getActivity().isDestroyed()) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(true);
                            onRefresh(); // 手动调用
                        }
                    }
                });
            }
        }, 250L);
    }

    @Override
    public void onRefresh() {
        Log.e("refreshLayout", "isRefreshing");
        if (refreshLayout.isRefreshing()) {
            // 获取苹果列表
            presenter.loadAppleList(getActivity(), 1000);
        }
    }

    @Override
    public void onLoadAppleListSuccess(List<Apple> appleList) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        appleAdapter.setAppleList(appleList);
    }

    /**
     * 切换 recyclerView 的布局方式
     */
    private void switchLayoutManager() {
        int dp4 = Math.round(RunboAppleApp.density * 4);
        int dp60 = Math.round(RunboAppleApp.density * 60);
        if (rvApple.getLayoutManager().equals(linearLayoutManager)) {
            /* 从 linear 切换到 grid */
            // 修改布局管理器
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            rvApple.setLayoutManager(gridLayoutManager);
            // 设置 padding
            rvApple.setPadding(dp4, dp4, dp4, dp60);
            // 清除缓存
            rvApple.getRecycledViewPool().clear();
            // 滚动到上次位置
            gridLayoutManager.scrollToPosition(position);
        } else {
            /* 从 grid 切换到 linear */
            // 修改布局管理器
            int position = gridLayoutManager.findFirstVisibleItemPosition();
            rvApple.setLayoutManager(linearLayoutManager);
            // 设置 padding
            rvApple.setPadding(0, dp4, 0, dp60);
            // 清除缓存
            rvApple.getRecycledViewPool().clear();
            // 滚动到上次位置
            linearLayoutManager.scrollToPosition(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment_apple, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fg_apple_switch_grid_list:
                switchLayoutManager();
                if (rvApple.getLayoutManager().equals(gridLayoutManager)) {
                    // 切换菜单
                    item.setTitle(R.string.list_layout);
                    item.setIcon(R.drawable.ic_action_list_white_24dp);
                } else {
                    // 切换菜单
                    item.setTitle(R.string.grid_layout);
                    item.setIcon(R.drawable.ic_action_grid_white_24dp);
                }
                break;
        }
        return false;
    }
}
