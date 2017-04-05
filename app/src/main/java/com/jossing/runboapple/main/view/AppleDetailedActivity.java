package com.jossing.runboapple.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jossing.runboapple.R;
import com.jossing.runboapple.main.adapter.ApplePicturePagerAdapter;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;
import com.jossing.runboapple.main.presenter.AppleDetailedPresenter;
import com.jossing.runboapple.main.presenter.IAppleDetailedPresenter;

import java.util.List;

public class AppleDetailedActivity extends AppCompatActivity
        implements IAppleDetailedActivity, View.OnClickListener {

    private IAppleDetailedPresenter presenter;

    private Toolbar toolbar;
    private ViewPager vpImv;
    private ApplePicturePagerAdapter pagerAdapter;
    private TextView tvName;
    private TextView tvQuality;
    private TextView tvAddress;
    private TextView tvDescription;
    private TextView tvCount;
    private TextView tvPrice;
    private Button btnWantBuy;

    private Apple apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_detailed);
        setToolbar();
        presenter = new AppleDetailedPresenter(this);
        apple = (Apple) getIntent().getSerializableExtra("apple");
        initWidget();
        showAppleDetailed();
    }

    /**
     * 替代 ActionBar 为 Toolbar
     */
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 实例化所有控件
     */
    private void initWidget() {
        vpImv = (ViewPager) findViewById(R.id.vp_imv);
        pagerAdapter = new ApplePicturePagerAdapter();
        vpImv.setAdapter(pagerAdapter);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvQuality = (TextView) findViewById(R.id.tv_quality);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        btnWantBuy = (Button) findViewById(R.id.btn_want_buy);
        btnWantBuy.setOnClickListener(this);

        vpImv.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPage", "Selected: " + position);
                /* 调用 adapter 中加载显示图片的方法 */
                pagerAdapter.loadApplePicture(AppleDetailedActivity.this, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 展示苹果信息
     */
    private void showAppleDetailed() {
        tvName.setText(apple.getName());
        String qualityStr = apple.getQuality() + " 级";
        tvQuality.setText(qualityStr);
        tvAddress.setText(apple.getAddress());
        tvDescription.setText(apple.getDescription());
        String countStr = " (" + apple.getCount() + " kg)";
        tvCount.setText(countStr);
        String priceStr = apple.getPrice() + "/kg";
        tvPrice.setText(priceStr);

        // 获取图片列表
        presenter.queryPictureList(this, apple);
    }

    @Override
    public void onQueryPictureListSuccess(List<ApplePicture> applePictureList) {
        pagerAdapter.setApplePictureList(this, applePictureList);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_want_buy:
                Intent intent = new Intent(this, ConferActivity.class);
                intent.putExtra("apple", apple);
                startActivity(intent);
                break;
        }
    }
}
