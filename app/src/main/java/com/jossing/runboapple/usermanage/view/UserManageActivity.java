package com.jossing.runboapple.usermanage.view;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jossing.runboapple.R;
import com.jossing.runboapple.RunboAppleApp;
import com.jossing.runboapple.usermanage.presenter.IUserManagePresenter;
import com.jossing.runboapple.usermanage.presenter.UserManagePresenter;

/**
 * Created by Somnus on 2017/3/30.
 */

public class UserManageActivity extends AppCompatActivity implements IUserManageActivity,View.OnClickListener{
    private IUserManagePresenter iLoginPresenter;
    private LinearLayout ll_login, ll_register;
    private View login_close_space, register_close_space;
    private Button login_bt_login, register_bt_register;
    private TextView login_tx_forget, login_tx_register;
    private ImageView login_close, register_close,register_back;
    private CoordinatorLayout coordinatorLayout;
    private EditText login_edit_username,login_edit_password,register_edit_username,register_edit_password;

    private int defaultAlpha = 0x8a;

    public enum ResultCode { LOGIN_SUCCESS, NOT_LOGIN }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int val0 = getWindow().getDecorView().getSystemUiVisibility();
        getWindow().getDecorView().setSystemUiVisibility(val0|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_usermanage);
        iLoginPresenter = new UserManagePresenter(this);
        init();

        login_bt_login.setOnClickListener(this);
        login_tx_forget.setOnClickListener(this);
        login_tx_register.setOnClickListener(this);
        login_close.setOnClickListener(this);
        login_close_space.setOnClickListener(this);
        register_back.setOnClickListener(this);
        register_close.setOnClickListener(this);
        register_close_space.setOnClickListener(this);
        register_bt_register.setOnClickListener(this);

        setBackgroundAlpha(defaultAlpha);

        /* 动画展开 bottom sheet */
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animChangeBgAlpha(true, animation.getDuration());
                // 设置两个 bottom sheet 为 Expanded
                BottomSheetBehavior.from(ll_login).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(ll_register).setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        ll_login.startAnimation(animation);
    }

    private void init(){
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.cl);

        ll_login = (LinearLayout) findViewById(R.id.ll_login);
        login_tx_forget = (TextView)findViewById(R.id.login_tx_forget);
        login_bt_login = (Button)findViewById(R.id.login_bt_login);
        login_edit_username = (EditText)findViewById(R.id.login_edit_username);
        login_edit_password = (EditText)findViewById(R.id.login_edit_password);
        login_close = (ImageView)findViewById(R.id.login_close);
        login_close_space = findViewById(R.id.login_close_space);
        login_tx_register = (TextView)findViewById(R.id.login_tx_register);
        BottomSheetBehavior.from(ll_login).setBottomSheetCallback(callback);

        ll_register = (LinearLayout) findViewById(R.id.ll_register);
        register_edit_username = (EditText)findViewById(R.id.register_edit_username);
        register_edit_password = (EditText)findViewById(R.id.register_edit_password);
        register_bt_register = (Button)findViewById(R.id.register_bt_register);
        register_back = (ImageView)findViewById(R.id.register_back);
        register_close = (ImageView)findViewById(R.id.register_close);
        register_close_space = findViewById(R.id.register_close_space);
        BottomSheetBehavior.from(ll_register).setBottomSheetCallback(callback);
    }

    @Override
    public void onBackPressed() {
        if (ll_register.getVisibility() == View.VISIBLE) {
            toLoginView();
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        // 动画关闭 bottom sheet
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animChangeBgAlpha(false, animation.getDuration());
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_login.setVisibility(View.GONE);
                ll_register.setVisibility(View.GONE);
                UserManageActivity.super.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        if (ll_login.getVisibility() == View.VISIBLE) {
            ll_login.startAnimation(animation);
        } else {
            ll_register.startAnimation(animation);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt_login:
                // 登录按钮
                String login_email = login_edit_username.getText().toString();
                String login_password = login_edit_password.getText().toString();
                iLoginPresenter.login(this,login_email,login_password);
                break;
            case R.id.register_bt_register:
                // 注册按钮
                String register_email = register_edit_username.getText().toString();
                String register_password = register_edit_password.getText().toString();
                iLoginPresenter.register(this, register_email, register_password);
                Log.w("info",register_email+"\n"+register_password);
                break;
            case R.id.login_tx_forget:
                // 忘记密码文本
                break;
            case R.id.login_tx_register:
                toRegisterView();
                break;
            case R.id.register_back:
                toLoginView();
                break;
            case R.id.login_close: // 登录界面关闭图片
            case R.id.register_close: // 注册界面关闭图片
            case R.id.login_close_space: // 登录界面
            case R.id.register_close_space: // 注册界面
                finish();
                break;
        }
    }

    /**
     * 注册界面返回登录界面
     */
    private void toLoginView() {
        Animation animation_left_in = AnimationUtils.loadAnimation(UserManageActivity.this, R.anim.slide_in_left);
        final Animation animation_right_out = AnimationUtils.loadAnimation(UserManageActivity.this,R.anim.slide_out_right);
        animation_left_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ll_login.setVisibility(View.VISIBLE);
                ll_register.startAnimation(animation_right_out);
                login_edit_username.setText("");
                login_edit_password.setText("");
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                ll_register.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        ll_login.startAnimation(animation_left_in);
    }

    /**
     * 登录界面跳转注册界面
     */
    private void toRegisterView() {
        final Animation animation_left_out = AnimationUtils.loadAnimation(UserManageActivity.this,R.anim.slide_out_left);
        Animation animation_right_in = AnimationUtils.loadAnimation(UserManageActivity.this,R.anim.slide_in_right);
        animation_right_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ll_register.setVisibility(View.VISIBLE);
                ll_login.startAnimation(animation_left_out);
                register_edit_username.setText("");
                register_edit_password.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_login.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        ll_register.startAnimation(animation_right_in);
    }

    /**
     * 动画改变窗口背景透明度
     * @param open true 代表需要渐变至全不透明，false 代表需要渐变至全透明
     */
    private void animChangeBgAlpha(boolean open, long duration) {
        ValueAnimator valueAnimator;
        if (open) {
            valueAnimator = ValueAnimator.ofInt(0, defaultAlpha).setDuration(duration);
        } else {
            valueAnimator = ValueAnimator.ofInt(defaultAlpha, 0).setDuration(duration);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setBackgroundAlpha((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置界面顶层 layout 的背景透明度
     * @param alpha 透明度
     */
    private void setBackgroundAlpha(int alpha) {
        coordinatorLayout.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }

    @Override
    public void loginSuccess() {
        setResult(ResultCode.LOGIN_SUCCESS.ordinal());
        finish();
        RunboAppleApp.toastShow(this,"登录成功",Toast.LENGTH_SHORT);
    }

    @Override
    public void loginFailure(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess() {
        toLoginView();
        RunboAppleApp.toastShow(this,"注册成功",Toast.LENGTH_SHORT);
    }

    @Override
    public void registerFailure(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }


    /**
     * 生成一个 bottom sheet callback
     */
    private BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {}

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            setBackgroundAlpha(Math.round(defaultAlpha * slideOffset));
            if (slideOffset == 0f) {
                UserManageActivity.super.finish();
            }
        }
    };
}
