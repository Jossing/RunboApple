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
import android.widget.Toast;

import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.comment.view.CommentActivity;
import com.jossing.runboapple.customview.ViewPagerWithIndicator;
import com.jossing.runboapple.main.adapter.ApplePicturePagerAdapter;
import com.jossing.runboapple.main.model.Apple;
import com.jossing.runboapple.main.model.ApplePicture;
import com.jossing.runboapple.main.presenter.AppleDetailedPresenter;
import com.jossing.runboapple.main.presenter.IAppleDetailedPresenter;
import com.jossing.runboapple.order.view.OrderActivity;
import com.jossing.runboapple.usermanage.model.User;
import com.jossing.runboapple.usermanage.view.PersonActivity;
import com.jossing.runboapple.usermanage.view.UserManageActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobUser;

public class AppleDetailedActivity extends AppCompatActivity
        implements IAppleDetailedActivity, View.OnClickListener {

    private IAppleDetailedPresenter presenter;

    private Toolbar toolbar;
    private ViewPagerWithIndicator vpImvWithIndicator;
    private ViewPager vpImv;
    private ApplePicturePagerAdapter pagerAdapter;
    private TextView tvName;
    private TextView tvQuality;
    private TextView tvAddress;
    private TextView tvSeller;
    private TextView tvComment;
    private TextView tvDescription;
    private TextView tvCount;
    private TextView tvPrice;
    private TextView tvCreatedAt;
    private Button btnWantBuy;

    private Apple apple;

    public enum RequestCode { LOGIN }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_detailed);
        setToolbar();
        presenter = new AppleDetailedPresenter(this);
        apple = (Apple) getIntent().getSerializableExtra("apple");
        initWidget();
        showAppleDetailed();

        presenter.queryApple(this, apple.getObjectId());
    }

    /**
     * 替换 ActionBar 为 Toolbar
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
        vpImvWithIndicator = (ViewPagerWithIndicator) findViewById(R.id.vp_imv_indicator);
        vpImv = vpImvWithIndicator.getViewPager();
        pagerAdapter = new ApplePicturePagerAdapter(); // 准备好 adapter 的数据再设置给 viewpager
        tvName = (TextView) findViewById(R.id.tv_name);
        tvQuality = (TextView) findViewById(R.id.tv_quality);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvSeller = (TextView) findViewById(R.id.tv_seller);
        tvSeller.setOnClickListener(this);
        tvComment = (TextView) findViewById(R.id.tv_comment);
        tvComment.setOnClickListener(this);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCreatedAt = (TextView) findViewById(R.id.tv_created_at);
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
        tvSeller.setText(apple.getSeller().getUsername());

        SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
        String createdAtStr = apple.getCreatedAt().substring(0, 16);
        try {
            Date createdAt = sdfFrom.parse(createdAtStr);
            createdAtStr = sdfTo.format(createdAt);
        } catch (ParseException e) {
            Log.e("parse createAt string", "error : " + e.getLocalizedMessage());
        }
        tvCreatedAt.setText(createdAtStr);

        // 查询评论总数
        presenter.queryCommentCount(this, apple.getObjectId());

        // 获取图片列表
        presenter.queryPictureList(this, apple);
    }

    @Override
    public void onQueryAppleDone(Apple apple) {
        this.apple = apple;
        showAppleDetailed();
    }

    @Override
    public void onQueryCommentCountDone(int count) {
        if (count != -1) {
            String comment = "用户评论 (" + count + ")";
            tvComment.setText(comment);
        }
    }

    @Override
    public void onQueryPictureListDone(List<ApplePicture> applePictureList) {
        ApplePicture applePicture = new ApplePicture();
        applePicture.setApple(apple);
        applePicture.setPicture(apple.getPicture());
        applePictureList.add(0, applePicture);
        pagerAdapter.setApplePictureList(this, applePictureList);

        // 设置适配器
        vpImv.setAdapter(pagerAdapter);
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
            case R.id.tv_seller:
                Intent intent1 = new Intent(this, PersonActivity.class);
                String sellerId = apple.getSeller().getObjectId();
                intent1.putExtra("anotherUserObjectId", sellerId);
                startActivity(intent1);
                break;
            case R.id.tv_comment:
                Intent intent3 = new Intent(this, CommentActivity.class);
                intent3.putExtra("apple", apple);
                /* 获取评论总数 */
                String str = tvComment.getText().toString();
                intent3.putExtra("commentCount", str.substring(5));
                startActivity(intent3);
                break;
            case R.id.btn_want_buy:
                User currentUser = BmobUser.getCurrentUser(this, User.class);
                if (currentUser != null) {
                    if (currentUser.getObjectId().equals(apple.getSeller().getObjectId())) {
                        RunboAppleApp.toastShow(this, "无法购买自己发布的苹果", Toast.LENGTH_SHORT);
                    } else if (apple.getCount() == 0) {
                        RunboAppleApp.toastShow(this, "这批苹果已经卖完了哦", Toast.LENGTH_SHORT);
                    } else  {
                        intentToOrderActivity();
                    }
                } else {
                    Intent intent2 = new Intent(this, UserManageActivity.class);
                    startActivityForResult(intent2, RequestCode.LOGIN.ordinal());
                    Toast.makeText(this, "登录后才可以购买苹果哦~", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode.LOGIN.ordinal()) {
            if (resultCode == UserManageActivity.ResultCode.LOGIN_SUCCESS.ordinal()) {
                User currentUser = BmobUser.getCurrentUser(this, User.class);
                if (currentUser != null) {
                    intentToOrderActivity();
                }
            }
        }
    }

    /**
     * 跳转到下单界面
     */
    private void intentToOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("apple", apple);
        startActivity(intent);
    }
}
