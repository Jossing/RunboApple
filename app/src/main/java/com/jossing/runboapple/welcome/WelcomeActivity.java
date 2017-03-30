package com.jossing.runboapple.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jossing.runboapple.R;
import com.jossing.runboapple.main.view.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer timer;

    private Button btnSkip;
    private ImageView imvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 12345
        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoStartMainActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果 timer 正在执行，就取消他
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skip:
                // btnSkip
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                startMainActivity();
                break;
        }
    }

    /*
     * 实例化、初始化所有控件
     */
    private void initWidget() {
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
        imvWelcome = (ImageView) findViewById(R.id.imv_welcome);

        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpoint(this, "getWelcomePicture", new CloudCodeListener() {
            @Override
            public void onSuccess(Object o) {
                // Log.e("response", o.toString());
                String pictureURL = o.toString();
                Glide.with(WelcomeActivity.this)
                        .load(pictureURL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //让 Glide 既缓存全尺寸又缓存其他尺寸
                        .error(R.drawable.ic_warning_black_128dp)
                        .centerCrop()
                        .into(imvWelcome);
            }
            @Override
            public void onFailure(int i, String s) {
                Log.e("error", "\ncode: " + i + "\nmsg: " + s);
            }
        });
    }

    /*
     * 延迟 3 秒后自动进入主界面
     */
    private void autoStartMainActivity() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startMainActivity();
            }
        };
        timer.schedule(timerTask, 3000);
    }

    /*
     * 启动主界面
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
