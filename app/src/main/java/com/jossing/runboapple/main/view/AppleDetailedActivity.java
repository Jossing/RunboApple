package com.jossing.runboapple.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jossing.runboapple.R;
import com.jossing.runboapple.main.presenter.AppleDetailedPresenter;
import com.jossing.runboapple.main.presenter.IAppleDetailedPresenter;
import com.jossing.runboapple.model.Apple;

public class AppleDetailedActivity extends AppCompatActivity
        implements IAppleDetailedActivity, View.OnClickListener {

    private IAppleDetailedPresenter presenter;

    private ViewPager vpImv;
    private ImageView imv;
    private TextView tvName;
    private TextView tvQuality;
    private TextView tvAddress;
    private TextView tvDescription;
    private TextView tvcount;
    private Button btnWantBuy;

    private Apple apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_detailed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new AppleDetailedPresenter(this);
        apple = (Apple) getIntent().getSerializableExtra("apple");
        initWidget();
        showAppleDetailed();
    }

    private void initWidget() {
        vpImv = (ViewPager) findViewById(R.id.vp_imv);
        imv = (ImageView) findViewById(R.id.imv);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvQuality = (TextView) findViewById(R.id.tv_quality);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvcount = (TextView) findViewById(R.id.tv_count);
        btnWantBuy = (Button) findViewById(R.id.btn_want_buy);
        btnWantBuy.setOnClickListener(this);
    }

    private void showAppleDetailed() {
        Glide.with(this).load(R.drawable.bg).centerCrop().into(imv);
        tvName.setText(apple.getName());
        String qualityStr = apple.getQuality() + " 级";
        tvQuality.setText(qualityStr);
        tvAddress.setText(apple.getAddress());
        tvDescription.setText(apple.getDescription());
        String countStr = apple.getCount() + " kg";
        tvcount.setText(countStr);
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
