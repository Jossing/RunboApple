package com.jossing.runboapple.usermanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApplication;
import com.jossing.runboapple.usermanage.presenter.IPersonPresenter;
import com.jossing.runboapple.usermanage.presenter.PersonPresenter;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Somnus on 2017/4/2.
 */

public class PersonActivity extends AppCompatActivity
        implements IPersonActivity,View.OnClickListener {

    private IPersonPresenter iPersonPresenter;
    private RelativeLayout personal_rl_avatar,personal_rl_nick,personal_rl_sex;
    private Button personal_btn_logout;
    private ImageView imv_avatar;
    private TextView personal_avatar, personal_tv_username, personal_tv_nick, personal_tv_sex;

    public enum ResultCode { LOGOUT_SUCCESS, NOT_LOGOUT }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iPersonPresenter = new PersonPresenter(this);
        initWidget();

        Intent intent = getIntent();
        boolean currentUser = intent.getBooleanExtra("isCurrentUser", false);
        if (currentUser) {
            personal_avatar.setOnClickListener(this);
            personal_tv_username.setOnClickListener(this);
            personal_tv_nick.setOnClickListener(this);
            personal_tv_sex.setOnClickListener(this);
            personal_btn_logout.setOnClickListener(this);
            iPersonPresenter.getCurrentUserInfo(this);
        } else {
            personal_avatar.setBackground(null);
            personal_tv_username.setBackground(null);
            personal_tv_nick.setBackground(null);
            personal_tv_sex.setBackground(null);
            personal_btn_logout.setVisibility(View.GONE);

            String anotherUserObjectId = intent.getStringExtra("anotherUserObjectId");
            if (anotherUserObjectId != null && !anotherUserObjectId.equals("")) {
                iPersonPresenter.getUserInfo(this, anotherUserObjectId);
            } else {
                finish();
                RunboAppleApplication.toastShow(this, "没有用户信息", Toast.LENGTH_SHORT);
            }
        }
    }

    private void initWidget(){
        personal_rl_avatar = (RelativeLayout) findViewById(R.id.personal_rl_avatar);
        personal_rl_nick = (RelativeLayout) findViewById(R.id.personal_rl_nick);
        personal_rl_sex = (RelativeLayout) findViewById(R.id.personal_rl_sex);
        personal_btn_logout = (Button) findViewById(R.id.personal_btn_exit);
        imv_avatar = (ImageView) findViewById(R.id.imv_avatar);
        personal_avatar = (TextView) findViewById(R.id.personal_avatar);
        personal_tv_username = (TextView) findViewById(R.id.personal_tv_username);
        personal_tv_nick = (TextView) findViewById(R.id.personal_tv_nick);
        personal_tv_sex = (TextView) findViewById(R.id.personal_tv_sex);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_avatar:
               // 头像条
                break;
            case R.id.personal_tv_username:
                // 用户名
                RunboAppleApplication.toastShow(this, "用户名不可修改", Toast.LENGTH_SHORT);
                break;
            case R.id.personal_tv_nick:
                // 昵称条
                break;
            case R.id.personal_tv_sex:
                // 性别条
                break;
            case R.id.personal_btn_exit:
                // 退出按钮
                BmobUser.logOut(getApplicationContext());
                RunboAppleApplication.toastShow(this, "已成功退出登录", Toast.LENGTH_LONG);
                setResult(ResultCode.LOGOUT_SUCCESS.ordinal());
                finish();
                break;
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

    @Override
    public void getUserInfoFailure(String msg) {
        RunboAppleApplication.toastShow(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showUserInfo(String username, String nick, String sex, BmobFile avatar) {
        personal_tv_username.setText(username);
        personal_tv_nick.setText(nick);
        personal_tv_sex.setText(sex);
        /* 用户头像可能为空，所以需要判断 */
        if (avatar != null) {
            String avatarURL = avatar.getFileUrl(this);
            Glide.with(this)
                    .load(avatarURL)
                    .centerCrop()
                    .placeholder(R.drawable.def_avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imv_avatar);
        }
    }
}
