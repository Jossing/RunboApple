package com.jossing.runboapple.welcome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.main.view.MainActivity;
import com.jossing.runboapple.welcome.presenter.IWelcomePresenter;
import com.jossing.runboapple.welcome.presenter.WelcomePresenter;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity
        implements View.OnClickListener, IWelcomeActivity {

    private IWelcomePresenter presenter;

    private Timer timer;

    private Button btnSkip;
    private ImageView imvWelcome;

    private final long DELAY = 3000; //自动跳转的延时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        presenter = new WelcomePresenter(this);

        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoStartMainActivity(DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skip:
                // btnSkip
                cancelTimer();
                startMainActivity();
                break;
        }
    }

    /**
     * 如果 timer 正在执行，就取消他
     */
    private void cancelTimer() {
        // 如果 timer 正在执行，就取消他
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 实例化、初始化所有控件
     */
    private void initWidget() {
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
        imvWelcome = (ImageView) findViewById(R.id.imv_welcome);
        presenter.showWelcomePicture(this);
    }

    /**
     * 显示广告图片
     * @param pictureURL 图片的 url 地址
     */
    @Override
    public void showWelcomePicture(String pictureURL) {
        Glide.with(this).load(pictureURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //让 Glide 既缓存全尺寸又缓存其他尺寸
                .error(R.drawable.ic_warning_black_128dp)
                .centerCrop()
                .into(imvWelcome);
    }

    /**
     * 延迟 delay 秒后自动进入主界面
     * @param delay 延时
     */
    private void autoStartMainActivity(long delay) {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startMainActivity();
            }
        };
        timer.schedule(timerTask, delay);
    }

    /**
     * 启动主界面
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
